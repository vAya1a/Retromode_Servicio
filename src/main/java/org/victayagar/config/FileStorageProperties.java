package org.victayagar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/*
La clase "FileStorageProperties" es una clase de configuración utilizada
para gestionar la propiedad relacionada con el almacenamiento de archivos. Esta clase se utiliza para definir y
acceder a la configuración de almacenamiento de archivos, como el directorio de carga de archivos.

En resumen, la clase "FileStorageProperties" proporciona un mecanismo para configurar y obtener
el directorio de carga de archivos utilizado en la aplicación.
*/


@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public FileStorageProperties() {
    }

    /**
     * Constructor de la clase FileStorageProperties.
     *
     * @param uploadDir Directorio de carga de archivos.
     */
    public FileStorageProperties(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    /**
     * Obtiene el directorio de carga de archivos.
     *
     * @return Directorio de carga de archivos.
     */
    public String getUploadDir() {
        return uploadDir;
    }

    /**
     * Establece el directorio de carga de archivos.
     *
     * @param uploadDir Directorio de carga de archivos.
     */
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}