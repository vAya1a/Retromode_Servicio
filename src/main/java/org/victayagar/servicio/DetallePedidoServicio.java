package org.victayagar.servicio;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.victayagar.entidad.DetallePedido;
import org.victayagar.repositorio.DetallePedidoRepositorio;

/*
En esta clase, se utiliza la anotación @Service para indicar que es un componente de
servicio gestionado por Spring. Además, se aplica la anotación @Transactional para indicar
que los métodos de este servicio deben ser ejecutados dentro de una transacción.

En el constructor se inyecta el repositorio correspondiente (DetallePedidoRepositorio),
y se asigna al atributo repositorio.

El método guardarDetalles() se encarga de guardar los detalles de un pedido. Recibe como
parámetro una colección de DetallePedido y utiliza el método saveAll() del repositorio para
guardar todos los detalles en la base de datos. Se incluye un comentario que describe su funcionalidad.
*/

@Service
@Transactional
public class DetallePedidoServicio {
    private final DetallePedidoRepositorio repositorio;

    public DetallePedidoServicio(DetallePedidoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Guarda los detalles de un pedido.
     *
     * @param detalle Detalles del pedido a guardar.
     */
    public void guardarDetalles(Iterable<DetallePedido> detalle) {
        this.repositorio.saveAll(detalle);
    }
}
