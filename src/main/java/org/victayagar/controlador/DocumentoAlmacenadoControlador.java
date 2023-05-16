package org.victayagar.controlador;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.victayagar.entidad.DocumentoAlmacenado;
import org.victayagar.servicio.DocumentoAlmacenadoService;
import org.victayagar.utilidades.GenericResponse;


@RestController
@RequestMapping("api/documento-almacenado")
public class DocumentoAlmacenadoControlador {
    private DocumentoAlmacenadoService servicio;

    public DocumentoAlmacenadoControlador(DocumentoAlmacenadoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public GenericResponse list() {
        return servicio.list();
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {
        return servicio.downloadByFileName(fileName, request);
    }

    @PostMapping
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