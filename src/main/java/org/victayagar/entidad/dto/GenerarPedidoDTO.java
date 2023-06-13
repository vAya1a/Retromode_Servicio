package org.victayagar.entidad.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.victayagar.entidad.Cliente;
import org.victayagar.entidad.DetallePedido;
import org.victayagar.entidad.Pedido;

/*
Este DTO se utiliza para agrupar y transferir información relacionada con la
generación de un pedido, incluyendo el propio pedido, los detalles del pedido y el cliente asociado.
*/
public class GenerarPedidoDTO {
    private Pedido pedido;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Iterable<DetallePedido> detallePedidos;
    private Cliente cliente;

    public GenerarPedidoDTO() {
    }

    /**
     * Obtiene el objeto Pedido asociado al DTO.
     *
     * @return El objeto Pedido asociado.
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * Establece el objeto Pedido asociado al DTO.
     *
     * @param pedido El objeto Pedido a establecer.
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * Obtiene los detalles del pedido.
     *
     * @return Los detalles del pedido.
     */
    public Iterable<DetallePedido> getDetallePedidos() {
        return detallePedidos;
    }

    /**
     * Establece los detalles del pedido.
     *
     * @param detallePedidos Los detalles del pedido a establecer.
     */
    public void setDetallePedidos(Iterable<DetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }

    /**
     * Obtiene el objeto Cliente asociado al DTO.
     *
     * @return El objeto Cliente asociado.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el objeto Cliente asociado al DTO.
     *
     * @param cliente El objeto Cliente a establecer.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
