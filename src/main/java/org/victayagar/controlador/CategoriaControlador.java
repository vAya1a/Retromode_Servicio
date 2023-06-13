package org.victayagar.controlador;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.victayagar.servicio.CategoriaServicio;
import org.victayagar.utilidades.GenericResponse;

@RestController
@RequestMapping("api/categoria")
public class CategoriaControlador {
    private final CategoriaServicio servicio;

    public CategoriaControlador(CategoriaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse listarCategoriasActivas() {
        return this.servicio.listarCategoriasActivas();
    }
}