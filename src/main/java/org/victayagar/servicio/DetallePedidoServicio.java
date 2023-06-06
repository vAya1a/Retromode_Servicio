package org.victayagar.servicio;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.victayagar.entidad.DetallePedido;
import org.victayagar.repositorio.DetallePedidoRepositorio;

@Service
@Transactional
public class DetallePedidoServicio {
    private final DetallePedidoRepositorio repositorio;

    public DetallePedidoServicio(DetallePedidoRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    //Metodo para guardar detalles del pedido
    public void guardarDetalles(Iterable<DetallePedido> detalle){
        this.repositorio.saveAll(detalle);
    }
}
