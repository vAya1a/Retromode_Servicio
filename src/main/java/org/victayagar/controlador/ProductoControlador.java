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

/*
Los comentarios de abajo explican brevemente qué hace cada método de la clase "ProductoControlador".
El método "listarProductosRecomendados" se utiliza para obtener una lista de productos recomendados.
El método "listarProductosPorCategoria" se utiliza para obtener una lista de productos pertenecientes
a una categoría específica.
*/

public class ProductoControlador {
    private final ProductoServicio servicio;

    public ProductoControlador(ProductoServicio servicio) {
        this.servicio = servicio;
    }

    /**
     * Devuelve una respuesta genérica que contiene una lista de productos recomendados.
     *
     * @return Respuesta genérica que contiene una lista de productos recomendados.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse listarProductosRecomendados() {
        return this.servicio.listarProductosRecomendados();
    }

    /**
     * Devuelve una respuesta genérica que contiene una lista de productos pertenecientes a una categoría específica.
     *
     * @param idC ID de la categoría para la cual se desea obtener los productos.
     * @return Respuesta genérica que contiene una lista de productos por categoría.
     */
    @GetMapping(value = "/{idC}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse listarProductosPorCategoria(@PathVariable int idC) {
        return this.servicio.listarProductosPorCategoria(idC);
    }
}
