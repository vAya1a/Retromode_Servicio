package org.victayagar.entidad.dto;

import org.victayagar.entidad.DetallePedido;
import org.victayagar.entidad.Pedido;

import java.util.ArrayList;
/*
La clase PedidoConDetallesDTO es un DTO (Objeto de Transferencia de Datos)
que se utiliza para representar un pedido junto con sus detalles asociados.
Esta clase se utiliza para transferir información entre diferentes capas de la aplicación,
generalmente en operaciones de consulta o visualización de pedidos junto con sus detalles.
*/

public class PedidoConDetallesDTO {
    // Constructor por defecto
    // Crea una instancia de Pedido vacía y una lista vacía de DetallePedido
    private Pedido pedido;
    private Iterable<DetallePedido> detallePedido;

    public PedidoConDetallesDTO() {
        // Constructor con parámetros
        // Permite establecer el Pedido y la lista de DetallePedido
        this.pedido = new Pedido();
        this.detallePedido = new ArrayList<>();
    }

    public PedidoConDetallesDTO(Pedido pedido, Iterable<DetallePedido> detallePedido) {
        this.pedido = pedido;
        this.detallePedido = detallePedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Iterable<DetallePedido> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(Iterable<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }
}
