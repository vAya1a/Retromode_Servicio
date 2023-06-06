package org.victayagar.servicio;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.victayagar.entidad.DetallePedido;
import org.victayagar.entidad.Pedido;
import org.victayagar.entidad.dto.GenerarPedidoDTO;
import org.victayagar.entidad.dto.PedidoConDetallesDTO;
import org.victayagar.repositorio.DetallePedidoRepositorio;
import org.victayagar.repositorio.PedidoRepositorio;
import org.victayagar.repositorio.ProductoRepositorio;
import org.victayagar.utilidades.GenericResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}
