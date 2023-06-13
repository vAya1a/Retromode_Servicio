package org.victayagar.repositorio;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.victayagar.entidad.Producto;

/*
La interfaz ProductoRepositorio extiende CrudRepository y proporciona
métodos para realizar operaciones de CRUD en la entidad Producto. Además,
se definen consultas personalizadas utilizando la anotación @Query.

El método listarProductosRecomendados recupera todos los productos que son
recomendados. Utiliza una consulta JPQL para seleccionar los productos donde
el campo recomendado sea igual a 1.

El método listarProductosPorCategoria recupera todos los productos de una categoría
específica. Utiliza una consulta JPQL para seleccionar los productos donde el campo
categoria.id sea igual al ID de categoría proporcionado.

El método actualizarStock actualiza el stock de un producto. Utiliza una consulta JPQL
modificadora para restar la cantidad proporcionada del campo stock del producto con
el ID especificado.
*/

public interface ProductoRepositorio extends CrudRepository<Producto, Integer> {
    // Obtener productos recomendados
    @Query("SELECT P FROM Producto P WHERE P.recomendado = 1")
    Iterable<Producto> listarProductosRecomendados();

    // Obtener productos por categoría
    @Query("SELECT P FROM Producto P WHERE P.categoria.id = :idC")
    Iterable<Producto> listarProductosPorCategoria(@Param("idC") int idC);

    // Actualizar el stock de un producto
    @Modifying
    @Query("UPDATE Producto P SET P.stock = stock-:cant WHERE P.id=:id")
    void actualizarStock(int cant, int id);
}