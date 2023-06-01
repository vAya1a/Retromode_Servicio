package org.victayagar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.victayagar.entidad.Producto;

public interface ProductoRepositorio extends CrudRepository<Producto, Integer> {
    @Query("SELECT P FROM Producto P WHERE P.recomendado = 1")
    Iterable<Producto> listarProductosRecomendados();

    @Query("SELECT P FROM Producto P WHERE P.categoria.id = :idC")
    Iterable<Producto> listarProductosPorCategoria(@Param("idC") int idC);
}
