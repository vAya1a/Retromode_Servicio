package org.victayagar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.victayagar.entidad.Usuario;

import java.util.Optional;

public interface UsuarioRepositorio extends CrudRepository<Usuario, Integer> {
    @Query("SELECT U FROM Usuario U WHERE U.email=:correo AND U.clave=:password")
    Optional<Usuario> login(String correo, String password);
}