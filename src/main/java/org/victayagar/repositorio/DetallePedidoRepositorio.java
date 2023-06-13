package org.victayagar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.victayagar.entidad.DetallePedido;
import org.victayagar.entidad.Producto;

public interface DetallePedidoRepositorio extends CrudRepository<DetallePedido, Integer> {
    @Query("SELECT DP FROM DetallePedido DP WHERE DP.pedido.id = :idP")
    Iterable<DetallePedido> findByPedido(@Param("idP") int idP);
    @Query(value = "SELECT SUM(dp.cantidad * dp.precio) AS Total FROM detalle_pedido dp JOIN pedido p ON p.id = dp.pedido_id WHERE p.cliente_id = :idCli AND p.id = :idOrden", nativeQuery = true)
    Double totalByIdCustomer(int idCli, int idOrden);
}
