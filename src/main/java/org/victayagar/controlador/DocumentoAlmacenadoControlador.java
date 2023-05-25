package org.victayagar.controlador;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.victayagar.entidad.DocumentoAlmacenado;
import org.victayagar.servicio.DocumentoAlmacenadoServicio;
import org.victayagar.utilidades.GenericResponse;



@RestController
@RequestMapping("api/documento-almacenado")
public class DocumentoAlmacenadoControlador {
    private DocumentoAlmacenadoServicio servicio;

    public DocumentoAlmacenadoControlador(DocumentoAlmacenadoServicio service) {
        this.servicio = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse list() {
        return servicio.list();
    }

    @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse find(@PathVariable Long id) {
        return null;
    }

    @GetMapping(value= "/download/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {
        return servicio.downloadByFileName(fileName, request);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse save(@ModelAttribute DocumentoAlmacenado obj) {
        return servicio.save(obj);
    }

    public GenericResponse update(Long aLong, DocumentoAlmacenado obj) {
        return null;
    }

    public GenericResponse delete(Long aLong) {
        return null;
    }
}