package org.victayagar.servicio;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.victayagar.repositorio.CategoriaRepositorio;
import org.victayagar.utilidades.GenericResponse;

import static org.victayagar.utilidades.Global.*;

/*
Esta clase representa el servicio relacionado con la entidad Categoria.
Se utiliza la anotación @Service para indicar que es un componente de
servicio gestionado por Spring. Además, se aplica la anotación @Transactional
para indicar que los métodos de este servicio deben ser ejecutados dentro de una transacción.

En el constructor se inyecta el repositorio correspondiente
(CategoriaRepositorio), y se asigna al atributo repositorio.

El método listarCategoriasActivas() devuelve una respuesta genérica
(GenericResponse) que contiene la lista de categorías activas obtenidas
del repositorio. Se incluye un comentario que describe su funcionalidad.
*/

@Service
@Transactional
public class CategoriaServicio {
    private final CategoriaRepositorio repositorio;

    public CategoriaServicio(CategoriaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene la lista de categorías activas.
     *
     * @return Respuesta genérica que contiene la lista de categorías activas.
     */
    public GenericResponse listarCategoriasActivas() {
        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, this.repositorio.listarCategoriasActivas());
    }
}
