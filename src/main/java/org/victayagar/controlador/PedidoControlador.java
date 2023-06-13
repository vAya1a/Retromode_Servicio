package org.victayagar.controlador;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.victayagar.entidad.dto.GenerarPedidoDTO;
import org.victayagar.entidad.dto.PedidoConDetallesDTO;
import org.victayagar.servicio.PedidoServicio;
import org.victayagar.utilidades.GenericResponse;

import java.util.List;

/*
Los comentarios de abajo
explican brevemente qué hace cada método de la clase "PedidoControlador".
El método "devolverMisComprasConDetalle" se utiliza para obtener una lista de
pedidos con detalles realizados por un cliente específico. El método "guardarPedido"
se utiliza para guardar un nuevo pedido. El método "anularPedido" se utiliza para
anular un pedido existente. El método "exportInvoice" se utiliza para exportar una
factura en formato PDF para un cliente y orden específicos.
*/


@RestController
@RequestMapping("api/pedido")
public class PedidoControlador {
    private final PedidoServicio servicio;

    public PedidoControlador(PedidoServicio servicio) {
        this.servicio = servicio;
    }

    /**
     * Devuelve una respuesta genérica que contiene la lista de pedidos con detalles realizados por un cliente específico.
     *
     * @param idCli ID del cliente para el que se desea obtener los pedidos.
     * @return Respuesta genérica que contiene la lista de pedidos con detalles.
     */
    @GetMapping(value = "/misPedidos/{idCli}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<List<PedidoConDetallesDTO>> devolverMisComprasConDetalle(@PathVariable int idCli) {
        return this.servicio.devolverMisCompras(idCli);
    }

    /**
     * Guarda un nuevo pedido y devuelve una respuesta genérica.
     *
     * @param dto Objeto DTO que contiene los detalles del pedido a guardar.
     * @return Respuesta genérica que indica el resultado de la operación.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse guardarPedido(@RequestBody GenerarPedidoDTO dto) {
        return this.servicio.guardarPedido(dto);
    }

    /**
     * Anula un pedido existente y devuelve una respuesta genérica.
     *
     * @param id ID del pedido a anular.
     * @return Respuesta genérica que indica el resultado de la operación.
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse anularPedido(@PathVariable int id) {
        return this.servicio.anularPedido(id);
    }

    /**
     * Exporta una factura en formato PDF para un cliente y orden específicos.
     *
     * @param idCli   ID del cliente para el que se desea exportar la factura.
     * @param idOrden ID de la orden para la cual se desea exportar la factura.
     * @return ResponseEntity que contiene el recurso ByteArrayResource de la factura en formato PDF.
     */
    @GetMapping(value = "exportInvoice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ByteArrayResource> exportInvoice(@RequestParam int idCli, @RequestParam int idOrden) {
        return this.servicio.exportInvoice(idCli, idOrden);
    }

}
