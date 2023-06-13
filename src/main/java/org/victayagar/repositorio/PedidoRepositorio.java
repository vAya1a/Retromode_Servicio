package org.victayagar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.victayagar.entidad.Pedido;

import java.util.Optional;

/*
La interfaz PedidoRepositorio extiende CrudRepository
y proporciona métodos para realizar operaciones de CRUD en la entidad
Pedido. También se definen consultas personalizadas utilizando la anotación @Query.

El método devolverMisCompras recupera las compras realizadas por un
cliente específico. Utiliza una consulta JPQL para buscar los
pedidos que tengan un cliente con el ID proporcionado.

El método findByIdAndClienteId busca un pedido por ID y ID de cliente.
Utiliza una consulta JPQL para buscar un pedido que tenga el ID de pedido
y el ID de cliente proporcionados. Devuelve un Optional que puede contener
el pedido encontrado o ser vacío si no se encuentra ningún pedido con esos valores de ID.
*/

public interface PedidoRepositorio extends CrudRepository<Pedido, Integer> {

    // Obtener las compras realizadas por un cliente específico
    @Query("SELECT P FROM Pedido P WHERE P.cliente.id = :idCli")
    Iterable<Pedido> devolverMisCompras(@Param("idCli") int idCli);

    // Buscar un pedido por ID y ID de cliente
    @Query("SELECT P FROM Pedido P WHERE P.id=:idOrden AND P.cliente.id=:idCli")
    Optional<Pedido> findByIdAndClienteId(int idCli, int idOrden);
}
