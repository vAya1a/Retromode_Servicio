package org.victayagar.servicio;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.victayagar.entidad.DetallePedido;
import org.victayagar.entidad.Pedido;
import org.victayagar.entidad.dto.GenerarPedidoDTO;
import org.victayagar.entidad.dto.PedidoConDetallesDTO;
import org.victayagar.repositorio.DetallePedidoRepositorio;
import org.victayagar.repositorio.PedidoRepositorio;
import org.victayagar.repositorio.ProductoRepositorio;
import org.victayagar.utilidades.GenericResponse;



import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.victayagar.utilidades.Global.*;

@Service
@Transactional
public class PedidoServicio {
    private final PedidoRepositorio repositorio;
    private final DetallePedidoRepositorio detallePedidoRepositorio;
    private final DetallePedidoServicio dpServicio;
    private final ProductoRepositorio pRepositorio;

    public PedidoServicio(PedidoRepositorio repositorio, DetallePedidoRepositorio detallePedidoRepositorio, DetallePedidoServicio dpServicio, ProductoRepositorio pRepositorio) {
        this.repositorio = repositorio;
        this.detallePedidoRepositorio = detallePedidoRepositorio;
        this.dpServicio = dpServicio;
        this.pRepositorio = pRepositorio;
    }

    //Metodo para devolver los pedidos con su respectivo detalle
    public GenericResponse<List<PedidoConDetallesDTO>> devolverMisCompras(int idCli) {
        final List<PedidoConDetallesDTO> dtos = new ArrayList<>();
        final Iterable<Pedido> pedidos = repositorio.devolverMisCompras(idCli);
        pedidos.forEach(p -> {
            dtos.add(new PedidoConDetallesDTO(p, detallePedidoRepositorio.findByPedido(p.getId())));
        });
        return new GenericResponse(OPERACION_CORRECTA, RPTA_OK, "Petición encontrada", dtos);
    }

    //Metodo para guardar el pedido
    public GenericResponse guardarPedido(GenerarPedidoDTO dto) {
        Date date = new Date();
        dto.getPedido().setFechaCompra(new java.sql.Date(date.getTime()));
        dto.getPedido().setAnularPedido(false);
        dto.getPedido().setMonto(dto.getPedido().getMonto());
        dto.getPedido().setCliente(dto.getCliente());
        this.repositorio.save(dto.getPedido());
        for (DetallePedido dp : dto.getDetallePedidos()) {
            dp.setPedido(dto.getPedido());
            this.pRepositorio.actualizarStock(dp.getCantidad(), dp.getProducto().getId());
        }
        //Llamamos al DetallePedidoServicio
        this.dpServicio.guardarDetalles(dto.getDetallePedidos());
        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, dto);
    }

    //Metodo para anular el pedido
    public GenericResponse anularPedido(int id) {
        Pedido p = this.repositorio.findById(id).orElse(new Pedido());
        if (p.getId() != 0) {
            p.setAnularPedido(true);
            this.repositorio.save(p);
            return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, p);
        } else {
            return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, "El pedido que desea anular no es valido");
        }
    }


    public ResponseEntity<ByteArrayResource> exportInvoice(int idCli, int idOrden) {
        Optional<Pedido> optPedido = this.repositorio.findByIdAndClienteId(idCli, idOrden);
        Double rpta = this.detallePedidoRepositorio.totalByIdCustomer(idCli, idOrden);
        if (optPedido.isPresent()) {
            try {
                final Pedido pedido = optPedido.get();
                final File file = ResourceUtils.getFile("classpath:exportInvoice.jasper");
                final File imgLogo = ResourceUtils.getFile("classpath:logo.jpg");
                final JasperReport report = (JasperReport) JRLoader.loadObject(file);

                final HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("nombreCliente", pedido.getCliente().getNombreCompletoCliente());
                parameters.put("imgLogo", new FileInputStream(imgLogo));
                parameters.put("total", rpta);
                parameters.put("dsInvoice", new JRBeanCollectionDataSource((Collection<?>) this.detallePedidoRepositorio.findByPedido(idOrden)));

                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
                byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
                String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
                StringBuilder stringBuilder = new StringBuilder().append("InvoicePDF:");
                ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                        .filename(stringBuilder.append(pedido.getId())
                                .append("generateDate:")
                                .append(sdf)
                                .append(".pdf")
                                .toString())
                        .build();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(contentDisposition);
                return ResponseEntity.ok().contentLength((long)reporte.length)
                        .contentType(MediaType.APPLICATION_PDF)
                        .headers(headers).body(new ByteArrayResource(reporte));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ResponseEntity.noContent().build(); //No se encontro el reporte
        }
        return null;
    }
}

