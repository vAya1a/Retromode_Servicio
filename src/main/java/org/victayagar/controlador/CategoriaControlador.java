package org.victayagar.controlador;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.victayagar.servicio.CategoriaServicio;
import org.victayagar.utilidades.GenericResponse;

/* La clase CategoriaControlador se utiliza como un controlador REST que maneja las solicitudes
relacionadas con las categorías y responde con una lista de categorías activas.
El método "listarCategoriasActivas" devuelve una respuesta genérica que contiene
las categorías activas obtenidas del servicio.
*/

@RestController
@RequestMapping("api/categoria")
public class CategoriaControlador {
    private final CategoriaServicio servicio;


    /**
     * Constructor de la clase CategoriaControlador.
     *
     * @param servicio Servicio de categoría utilizado para obtener las categorías activas.
     */
    public CategoriaControlador(CategoriaServicio servicio) {
        this.servicio = servicio;
    }


    /**
     * Obtiene una respuesta genérica que lista las categorías activas.
     *
     * @return Respuesta genérica que contiene las categorías activas.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse listarCategoriasActivas() {
        return this.servicio.listarCategoriasActivas();
    }
}