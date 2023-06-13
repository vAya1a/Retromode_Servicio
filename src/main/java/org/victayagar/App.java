package org.victayagar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.victayagar.config.FileStorageProperties;


/*
La clase App es la clase principal que inicia la aplicación Spring Boot.
Está anotada con @SpringBootApplication, lo que indica que es una aplicación
Spring Boot y se encarga de la configuración automática de la aplicación.
La anotación @EnableConfigurationProperties({FileStorageProperties.class})
se utiliza para habilitar la carga de propiedades de configuración definidas
en la clase FileStorageProperties.

El método main es el punto de entrada de la aplicación. Llama al método run de la
clase SpringApplication, pasando la clase App y los argumentos de línea de comandos
(args). Esto inicia la aplicación Spring Boot y la pone en funcionamiento.
*/
@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}