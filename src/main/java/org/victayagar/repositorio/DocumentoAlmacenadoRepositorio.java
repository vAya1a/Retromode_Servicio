package org.victayagar.repositorio;

import org.victayagar.entidad.DocumentoAlmacenado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/*
La interfaz DocumentoAlmacenadoRepositorio extiende CrudRepository y
proporciona métodos para realizar operaciones de CRUD en la entidad
DocumentoAlmacenado. También se definen consultas personalizadas
utilizando la anotación @Query.

El método list recupera la lista de documentos almacenados
que tienen estado 'A' y no están eliminados. Utiliza una consulta
JPQL para filtrar los documentos según el estado y la propiedad eliminado.

El método findByFileName busca un documento almacenado por
nombre de archivo. Utiliza una consulta JPQL para buscar
un documento que tenga el nombre de archivo proporcionado y
cumpla con los criterios de estado 'A' y no eliminado. Devuelve
un Optional que puede contener el documento encontrado o ser
vacío si no se encuentra ningún documento con ese nombre de archivo.
*/

public interface DocumentoAlmacenadoRepositorio extends CrudRepository<DocumentoAlmacenado, Long> {
    // Obtener la lista de documentos almacenados con estado 'A' y no eliminados
    @Query("SELECT da FROM DocumentoAlmacenado da WHERE da.estado = 'A' AND da.eliminado = false")
    Iterable<DocumentoAlmacenado> list();

    // Buscar un documento almacenado por nombre de archivo con estado 'A' y no eliminado
    @Query("SELECT da FROM DocumentoAlmacenado da WHERE da.fileName = :fileName AND da.estado = 'A' AND da.eliminado = false")
    Optional<DocumentoAlmacenado> findByFileName(String fileName);
}