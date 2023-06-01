package org.victayagar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.victayagar.entidad.Categoria;

public interface CategoriaRepositorio extends CrudRepository<Categoria, Integer> {

    @Query("SELECT c FROM Categoria c WHERE c.vigencia = 1")
    Iterable<Categoria> listarCategoriasActivas();
}
