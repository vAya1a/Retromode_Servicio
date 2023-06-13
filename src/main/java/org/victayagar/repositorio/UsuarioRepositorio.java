package org.victayagar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.victayagar.entidad.Usuario;

import java.util.Optional;

/*
La interfaz UsuarioRepositorio extiende CrudRepository y proporciona
métodos para realizar operaciones de CRUD en la entidad Usuario. Además,
se define una consulta personalizada utilizando la anotación @Query.

El método login realiza el inicio de sesión de un usuario. Utiliza una consulta
JPQL para seleccionar el usuario cuyo campo email coincida con el correo
electrónico proporcionado y cuyo campo clave coincida con la contraseña proporcionada.
Retorna un objeto Optional<Usuario> que puede contener el usuario encontrado o ser
vacío en caso de que no se encuentre ningún usuario con las credenciales proporcionadas.
*/

public interface UsuarioRepositorio extends CrudRepository<Usuario, Integer> {
    // Realizar inicio de sesión
    @Query("SELECT U FROM Usuario U WHERE U.email=:correo AND U.clave=:password")
    Optional<Usuario> login(String correo, String password);
}