package org.victayagar.controlador;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.victayagar.entidad.DocumentoAlmacenado;
import org.victayagar.servicio.DocumentoAlmacenadoServicio;
import org.victayagar.utilidades.GenericResponse;


/*
¿Qué hace cada método de la clase "DocumentoAlmacenadoControlador"?
 Los métodos "list" y "find" se utilizan para obtener una lista de documentos
 almacenados y encontrar un documento almacenado por su ID, respectivamente.
 El método "download" se utiliza para descargar un documento almacenado por su
 nombre de archivo. El método "save" se utiliza para guardar un documento almacenado.
 Los métodos "update" y "delete" no tienen implementación proporcionada en el código que se ha compartido.
 */

@RestController
@RequestMapping("api/documento-almacenado")
public class DocumentoAlmacenadoControlador {
    private DocumentoAlmacenadoServicio servicio;

    /**
     * Constructor de la clase DocumentoAlmacenadoControlador.
     *
     * @param service Servicio de documento almacenado utilizado para realizar operaciones relacionadas con los documentos almacenados.
     */
    public DocumentoAlmacenadoControlador(DocumentoAlmacenadoServicio service) {
        this.servicio = service;
    }

    /**
     * Obtiene una respuesta genérica que lista los documentos almacenados.
     *
     * @return Respuesta genérica que contiene los documentos almacenados.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse list() {
        return servicio.list();
    }

    /**
     * Obtiene una respuesta genérica que encuentra un documento almacenado por su ID.
     *
     * @param id ID del documento almacenado a encontrar.
     * @return Respuesta genérica que contiene el documento almacenado encontrado.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse find(@PathVariable Long id) {
        return null;
    }

    /**
     * Descarga un documento almacenado por su nombre de archivo.
     *
     * @param fileName Nombre de archivo del documento almacenado a descargar.
     * @param request  HttpServletRequest para obtener el contexto de la solicitud.
     * @return ResponseEntity que representa la respuesta de descarga del archivo.
     */
    @GetMapping(value = "/download/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {
        return servicio.downloadByFileName(fileName, request);
    }

    /**
     * Guarda un documento almacenado y devuelve una respuesta genérica.
     *
     * @param obj Documento almacenado a guardar.
     * @return Respuesta genérica que indica el resultado de la operación.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse save(@ModelAttribute DocumentoAlmacenado obj) {
        return servicio.save(obj);
    }

    // Los siguientes métodos no tienen implementación proporcionada
    public GenericResponse update(Long aLong, DocumentoAlmacenado obj) {
        return null;
    }

    public GenericResponse delete(Long aLong) {
        return null;
    }
}