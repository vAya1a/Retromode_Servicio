package org.victayagar.servicio;


import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.victayagar.entidad.DocumentoAlmacenado;
import org.victayagar.repositorio.DocumentoAlmacenadoRepositorio;
import org.victayagar.utilidades.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.HashMap;

import static org.victayagar.utilidades.Global.*;


/*
En esta clase, se utiliza la anotación @Service para indicar que es un componente de
servicio gestionado por Spring. Además, se aplica la anotación @Transactional para indicar
que los métodos de este servicio deben ser ejecutados dentro de una transacción.

En el constructor se inyectan el repositorio correspondiente (DocumentoAlmacenadoRepositorio)
y el servicio de almacenamiento de archivos (FileStorageServicio), y se asignan a los atributos
repo y storageService, respectivamente.

El método list() devuelve una respuesta genérica con una lista de todos los documentos almacenados.
Se incluye un comentario que describe su funcionalidad.

El método save() se encarga de guardar un documento almacenado. Recibe como parámetro el documento a
guardar, realiza el almacenamiento del archivo adjunto utilizando el servicio de almacenamiento de
archivos y actualiza el nombre del archivo y su extensión en el objeto. Finalmente, devuelve una
respuesta genérica con el documento almacenado guardado. Se incluye un comentario que describe su funcionalidad.

El método download() permite descargar un documento almacenado. Recibe como parámetros el nombre
completo del archivo a descargar y el objeto HttpServletRequest. Utiliza el servicio de almacenamiento
de archivos para cargar el recurso del archivo y obtiene el tipo de contenido (content type) del archivo.
A continuación, construye y devuelve una respuesta ResponseEntity con el recurso del archivo para su descarga.
Se incluye un comentario que describe su funcionalidad.

El método downloadByFileName() permite descargar un documento almacenado por su nombre de archivo.
Recibe como parámetros el nombre de archivo y el objeto HttpServletRequest. Busca el documento almacenado
correspondiente al nombre de archivo proporcionado y llama al método download() para realizar la descarga.
Se incluye un comentario que describe su funcionalidad.

Los métodos find(), delete() y validate() no tienen implementación y devuelven valores nulos.
*/

@Service
@Transactional
public class DocumentoAlmacenadoServicio {
    private DocumentoAlmacenadoRepositorio repo;
    private FileStorageServicio storageService;

    public DocumentoAlmacenadoServicio(DocumentoAlmacenadoRepositorio repo, FileStorageServicio storageService) {
        this.repo = repo;
        this.storageService = storageService;
    }

    /**
     * Obtiene una lista de todos los documentos almacenados.
     *
     * @return Respuesta genérica con la lista de documentos almacenados.
     */
    public GenericResponse<Iterable<DocumentoAlmacenado>> list() {
        return new GenericResponse<Iterable<DocumentoAlmacenado>>(TIPO_RESULT, RPTA_OK, OPERACION_CORRECTA, repo.list());
    }


    public GenericResponse find(Long aLong) {
        return null;
    }


    /**
     * Guarda un documento almacenado.
     *
     * @param obj Documento almacenado a guardar.
     * @return Respuesta genérica con el documento almacenado guardado.
     */
    public GenericResponse save(DocumentoAlmacenado obj) {
        String fileName = (repo.findById(obj.getId())).orElse(new DocumentoAlmacenado()).getFileName();

        String originalFilename = obj.getFile().getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        fileName = storageService.storeFile(obj.getFile(), fileName);

        obj.setFileName(fileName);
        obj.setExtension(extension);

        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, repo.save(obj));
    }

    /**
     * Descarga un documento almacenado.
     *
     * @param completefileName Nombre completo del archivo a descargar.
     * @param request          HttpServletRequest.
     * @return ResponseEntity con el recurso del archivo a descargar.
     */
    public ResponseEntity<Resource> download(String completefileName, HttpServletRequest request) {
        Resource resource = storageService.loadResource(completefileName);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * Descarga un documento almacenado por su nombre de archivo.
     *
     * @param fileName Nombre de archivo del documento a descargar.
     * @param request  HttpServletRequest.
     * @return ResponseEntity con el recurso del archivo a descargar.
     */
    public ResponseEntity<Resource> downloadByFileName(String fileName, HttpServletRequest request) {
        DocumentoAlmacenado doc = repo.findByFileName(fileName).orElse(new DocumentoAlmacenado());
        return download(doc.getCompleteFileName(), request);
    }


    public GenericResponse delete(Long aLong) {
        return null;
    }

    public HashMap<String, Object> validate(DocumentoAlmacenado obj) {
        return null;
    }
}