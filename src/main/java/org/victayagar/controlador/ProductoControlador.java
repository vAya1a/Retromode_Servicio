package org.victayagar.controlador;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.victayagar.servicio.ProductoServicio;
import org.victayagar.utilidades.GenericResponse;

@RestController
@RequestMapping("api/producto")
public class ProductoControlador {
    private final ProductoServicio servicio;

    public ProductoControlador(ProductoServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse listarProductosRecomendados() {
        return this.servicio.listarProductosRecomendados();
    }

    @GetMapping(value = "/{idC}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse listarProductosPorCategoria(@PathVariable int idC) {
        return this.servicio.listarProductosPorCategoria(idC);
    }
}
