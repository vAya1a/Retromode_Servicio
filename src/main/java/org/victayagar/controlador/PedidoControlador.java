package org.victayagar.controlador;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.victayagar.entidad.dto.GenerarPedidoDTO;
import org.victayagar.entidad.dto.PedidoConDetallesDTO;
import org.victayagar.servicio.PedidoServicio;
import org.victayagar.utilidades.GenericResponse;

import java.util.List;

@RestController
@RequestMapping("api/pedido")
public class PedidoControlador {
    private final PedidoServicio servicio;

    public PedidoControlador(PedidoServicio servicio) {
        this.servicio = servicio;
    }
    //LISTAR PEDIDOS CON DETALLES
    @GetMapping(value = "/misPedidos/{idCli}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<List<PedidoConDetallesDTO>> devolverMisComprasConDetalle(@PathVariable int idCli){
        return this.servicio.devolverMisCompras(idCli);
    }
    //Guardar pedido
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse guardarPedido(@RequestBody GenerarPedidoDTO dto){
        return this.servicio.guardarPedido(dto);
    }
    //Anular pedido
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse anularPedido(@PathVariable int id){
        return this.servicio.anularPedido(id);
    }

}
