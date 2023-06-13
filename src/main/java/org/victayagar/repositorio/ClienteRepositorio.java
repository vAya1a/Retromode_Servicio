package org.victayagar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.victayagar.entidad.Cliente;

/*
La interfaz ClienteRepositorio extiende CrudRepository y proporciona
métodos para realizar operaciones de CRUD en la entidad Cliente. Además,
se definen consultas personalizadas utilizando la anotación @Query.

El método existByDoc verifica si existe un cliente con el número de
documento especificado. Utiliza una consulta nativa para verificar
la existencia y devuelve un entero que indica si el cliente existe o no.

El método existByDocForUpdate verifica si existe otro cliente con el
mismo número de documento al realizar una actualización. También
utiliza una consulta nativa y toma en cuenta el ID del cliente
actual para evitar que se detecte como un duplicado en caso de que el
cliente esté actualizando su propio número de documento. Devuelve un
entero que indica si existe o no otro cliente con el mismo número de documento.
*/

public interface ClienteRepositorio extends CrudRepository<Cliente, Integer> {
    // Verificar si existe un cliente con el número de documento especificado
    @Query(value = "(SELECT EXISTS(SELECT id FROM cliente WHERE num_doc=:dni))", nativeQuery = true)
    int existByDoc(String dni);

    // Verificar si existe otro cliente con el mismo número de documento al realizar una actualización
    @Query(value = "SELECT EXISTS(SELECT C.* FROM cliente C WHERE C.num_doc=:dni AND NOT (C.id=:id))", nativeQuery = true)
    int existByDocForUpdate(String dni, int id);
}

