package org.victayagar.servicio;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.victayagar.config.FileStorageProperties;
import org.victayagar.exception.FileStorageException;
import org.victayagar.exception.MyFileNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/*En esta clase, se utiliza la anotación @Service para indicar que es un componente
de servicio gestionado por Spring.

En el constructor se inyecta la configuración de almacenamiento de archivos (FileStorageProperties)
y se asigna al atributo fileStorageProperties.

El método storeFile() se encarga de almacenar un archivo en el sistema de archivos.
Recibe como parámetros el archivo a almacenar y un nombre de archivo personalizado
(opcional). Se obtiene el nombre original del archivo, se extrae la extensión y se genera
un nombre de archivo único si no se proporcionó uno. Luego se obtiene la ubicación de
almacenamiento del archivo y se copia el archivo en esa ubicación. Finalmente, se devuelve
el nombre de archivo generado o actualizado. Se incluye un comentario que describe su funcionalidad.

Los métodos getFolderName() y getFileStorageLocation() son métodos auxiliares utilizados por
storeFile() para obtener el nombre de la carpeta y la ubicación de almacenamiento del archivo,
respectivamente.

El método loadResource() carga un recurso (archivo) desde el sistema de archivos. Recibe como
parámetro el nombre completo del archivo a cargar. Se obtiene la ubicación de almacenamiento
del archivo y se construye un objeto UrlResource con la ruta del archivo. Se verifica si el
recurso existe y se devuelve. Si el recurso no existe, se lanza una excepción MyFileNotFoundException.
Si ocurre un error al intentar acceder al archivo, se lanza una excepción MyFileNotFoundException.
Se incluye un comentario que describe su funcionalidad.*/

@Service
public class FileStorageServicio {
    private FileStorageProperties fileStorageProperties;

    public FileStorageServicio(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
    }

    /**
     * Almacena un archivo en el sistema de archivos.
     *
     * @param file     Archivo a almacenar.
     * @param fileName Nombre de archivo personalizado.
     * @return Nombre de archivo generado o actualizado.
     * @throws FileStorageException Si ocurre un error al almacenar el archivo.
     */
    public String storeFile(MultipartFile file, String fileName) {
        String originalName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = originalName.substring(originalName.lastIndexOf("."));

        if (fileName.equals("")) {
            fileName = UUID.randomUUID().toString();
        }

        Path fileStorageLocation = getFileStorageLocation(getFolderName(originalName));
        Path targetLocation = fileStorageLocation.resolve(fileName + extension);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;

        } catch (IOException e) {
            throw new FileStorageException("No se pudo almacenar el archivo", e);
        }
    }

    /**
     * Obtiene el nombre de la carpeta según la extensión del archivo.
     *
     * @param completeFileName Nombre completo del archivo.
     * @return Nombre de la carpeta.
     */
    private String getFolderName(String completeFileName) {
        String extension = completeFileName.substring(completeFileName.lastIndexOf("."));
        return extension.replace(".", "").toUpperCase();
    }

    /**
     * Obtiene la ubicación de almacenamiento del archivo según el nombre de la carpeta.
     *
     * @param folderName Nombre de la carpeta.
     * @return Ubicación de almacenamiento del archivo.
     * @throws FileStorageException Si no se puede crear el directorio.
     */
    private Path getFileStorageLocation(String folderName) {
        Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir() + "/" + folderName).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStorageLocation);
            return fileStorageLocation;
        } catch (IOException e) {
            throw new FileStorageException("No se pudo crear el directorio", e);
        }
    }

    /**
     * Carga un recurso (archivo) desde el sistema de archivos.
     *
     * @param completeFileName Nombre completo del archivo a cargar.
     * @return Recurso (archivo) cargado.
     * @throws MyFileNotFoundException Si el archivo no existe.
     * @throws MyFileNotFoundException Si ocurre un error al intentar acceder al archivo.
     */
    public Resource loadResource(String completeFileName) {
        Path fileStorageLocation = getFileStorageLocation(getFolderName(completeFileName));
        Path path = fileStorageLocation.resolve(completeFileName).normalize();
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("Archivo no encontrado: " + completeFileName);
            }

        } catch (MalformedURLException e) {
            throw new MyFileNotFoundException("Ha ocurrido un error al intentar acceder al archivo: " + completeFileName, e);
        }
    }
}