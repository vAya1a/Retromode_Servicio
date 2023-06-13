package org.victayagar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.victayagar.entidad.Categoria;

/*
La interfaz CategoriaRepositorio extiende CrudRepository y proporciona métodos para realizar
operaciones de CRUD en la entidad Categoria. Además, se define una consulta personalizada
utilizando la anotación @Query y el lenguaje de consulta de Spring Data JPA para obtener
todas las categorías activas (aquellas con vigencia igual a 1) de la base de datos.
*/

public interface CategoriaRepositorio extends CrudRepository<Categoria, Integer> {

    // Consulta personalizada para obtener todas las categorías activas
    @Query("SELECT c FROM Categoria c WHERE c.vigencia = 1")
    Iterable<Categoria> listarCategoriasActivas();
}
