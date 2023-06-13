package org.victayagar.servicio;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.victayagar.repositorio.ProductoRepositorio;
import org.victayagar.utilidades.GenericResponse;

import static org.victayagar.utilidades.Global.*;


/*
En esta clase, se utiliza la anotación @Service para indicar que es un componente
de servicio gestionado por Spring.

En el constructor se inyecta el repositorio necesario.

El método listarProductosRecomendados() devuelve una lista de productos recomendados.
Se realiza una consulta en el repositorio para obtener la lista de productos recomendados
y se devuelve como parte de una respuesta genérica.

El método listarProductosPorCategoria() devuelve una lista de productos filtrada por categoría.
Recibe como parámetro el ID de la categoría y realiza una consulta en el repositorio para
obtener la lista de productos correspondientes a esa categoría. La lista se devuelve como
parte de una respuesta genérica.
*/
@Service
@Transactional
public class ProductoServicio {
    private final ProductoRepositorio repositorio;

    public ProductoServicio(ProductoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de productos recomendados.
     *
     * @return Respuesta genérica con la lista de productos recomendados.
     */
    public GenericResponse listarProductosRecomendados() {
        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, this.repositorio.listarProductosRecomendados());
    }

    /**
     * Obtiene una lista de productos por categoría.
     *
     * @param idC ID de la categoría.
     * @return Respuesta genérica con la lista de productos de la categoría.
     */
    public GenericResponse listarProductosPorCategoria(int idC) {
        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, this.repositorio.listarProductosPorCategoria(idC));
    }
}
