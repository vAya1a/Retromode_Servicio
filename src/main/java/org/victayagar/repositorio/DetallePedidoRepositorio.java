package org.victayagar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.victayagar.entidad.DetallePedido;

/*
La interfaz DetallePedidoRepositorio extiende CrudRepository
y proporciona métodos para realizar operaciones de CRUD en la
entidad DetallePedido. También se definen consultas personalizadas
utilizando la anotación @Query.

El método findByPedido recupera los detalles de pedido asociados a
un ID de pedido específico. Utiliza una consulta JPQL para buscar
los detalles de pedido según el ID de pedido proporcionado.

El método totalByIdCustomer calcula el total del pedido para un
ID de cliente y un ID de orden específicos. Utiliza una consulta
nativa que realiza una combinación de tablas y calcula el total
multiplicando la cantidad por el precio de cada detalle de pedido
y sumándolos. Devuelve el total como un valor Double.
*/


public interface DetallePedidoRepositorio extends CrudRepository<DetallePedido, Integer> {
    // Obtener los detalles de pedido por ID de pedido
    @Query("SELECT DP FROM DetallePedido DP WHERE DP.pedido.id = :idP")
    Iterable<DetallePedido> findByPedido(@Param("idP") int idP);

    // Calcular el total del pedido por ID de cliente y ID de orden
    @Query(value = "SELECT SUM(dp.cantidad * dp.precio) AS Total FROM detalle_pedido dp JOIN pedido p ON p.id = dp.pedido_id WHERE p.cliente_id = :idCli AND p.id = :idOrden", nativeQuery = true)
    Double totalByIdCustomer(int idCli, int idOrden);
}
