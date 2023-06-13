package org.victayagar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
La clase "WebConfig" es una clase de configuración que implementa la interfaz "WebMvcConfigurer".
Esta clase se encarga de configurar el manejo de CORS (Cross-Origin Resource Sharing) en la aplicación web.

El método "addCorsMappings" dentro de esta clase se utiliza para definir las reglas de CORS, lo que permite
el acceso controlado a recursos desde un origen específico. En este caso, se configura para permitir solicitudes
desde "http://localhost:9090" y se especifican los métodos HTTP permitidos, como GET, POST, PUT, DELETE y HEAD.
También se permite el envío de credenciales en las solicitudes.

En resumen, la clase "WebConfig" se encarga de habilitar y configurar las reglas de CORS en la aplicación web,
lo que permite un acceso controlado a los recursos desde un origen específico.
*/


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura las reglas de CORS (Cross-Origin Resource Sharing) para permitir el acceso desde un origen específico.
     *
     * @param registry Objeto CorsRegistry utilizado para configurar las reglas de CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:9090")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                .allowCredentials(true);
    }
}
