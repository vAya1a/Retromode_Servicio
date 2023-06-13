package org.victayagar.servicio;


import jakarta.transaction.Transactional;
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

/*
En esta clase, se utiliza la anotación @Service para indicar que es un componente de servicio gestionado por Spring.

En el constructor se inyectan los repositorios y servicios necesarios.

El método devolverMisCompras() devuelve una lista de pedidos con sus respectivos detalles
para un cliente dado. Se realiza una consulta en el repositorio para obtener los pedidos y se
construye la lista de DTO PedidoConDetallesDTO que contiene el pedido y sus detalles.

El método guardarPedido() guarda un nuevo pedido en la base de datos. Se establece la fecha
de compra, se guarda el pedido y se actualiza el stock de los productos correspondientes.

El método anularPedido() anula un pedido existente. Se busca el pedido en la base de datos, se marca como anulado y se guarda.

El método exportInvoice() exporta una factura en formato PDF para un cliente y un pedido dado.
Se carga un archivo JasperReport que contiene la plantilla del informe, se establecen los parámetros
necesarios y se genera el informe en formato PDF utilizando JasperReports. El informe resultante se
devuelve como una respuesta de tipo ResponseEntity con el contenido del PDF.
*/

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

    /**
     * Devuelve los pedidos con su respectivo detalle para un cliente dado.
     *
     * @param idCli ID del cliente.
     * @return Respuesta genérica con la lista de pedidos y sus detalles.
     */
    public GenericResponse<List<PedidoConDetallesDTO>> devolverMisCompras(int idCli) {
        final List<PedidoConDetallesDTO> dtos = new ArrayList<>();
        final Iterable<Pedido> pedidos = repositorio.devolverMisCompras(idCli);
        pedidos.forEach(p -> {
            dtos.add(new PedidoConDetallesDTO(p, detallePedidoRepositorio.findByPedido(p.getId())));
        });
        return new GenericResponse(OPERACION_CORRECTA, RPTA_OK, "Petición encontrada", dtos);
    }

    /**
     * Guarda un pedido en la base de datos.
     *
     * @param dto DTO que contiene la información del pedido y sus detalles.
     * @return Respuesta genérica con el resultado de la operación.
     */
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

    /**
     * Anula un pedido.
     *
     * @param id ID del pedido a anular.
     * @return Respuesta genérica con el resultado de la operación.
     */
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

    /**
     * Exporta una factura en formato PDF para un cliente y un pedido dado.
     *
     * @param idCli   ID del cliente.
     * @param idOrden ID del pedido.
     * @return Respuesta de tipo ResponseEntity que contiene el archivo PDF.
     */
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
                return ResponseEntity.ok().contentLength((long) reporte.length)
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

