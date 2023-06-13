package org.victayagar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.victayagar.entidad.Pedido;

import java.util.Optional;

public interface PedidoRepositorio extends CrudRepository<Pedido, Integer> {
    @Query("SELECT P FROM Pedido P WHERE P.cliente.id = :idCli")
    Iterable<Pedido> devolverMisCompras(@Param("idCli") int idCli);

    @Query("SELECT P FROM Pedido P WHERE P.id=:idOrden AND P.cliente.id=:idCli")
    Optional<Pedido> findByIdAndClienteId(int idCli, int idOrden);
}
