package org.victayagar.controlador;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.victayagar.entidad.Usuario;
import org.victayagar.servicio.UsuarioServicio;
import org.victayagar.utilidades.GenericResponse;

/*
Los comentarios de abajo describen brevemente qué hace cada método
de la clase "UsuarioControlador". El método "login" se utiliza
para realizar el inicio de sesión de un usuario. El método "save"
se utiliza para guardar un nuevo usuario. Y el método "update"
se utiliza para actualizar un usuario existente.
*/


@RestController
@RequestMapping("api/usuario")
public class UsuarioControlador {
    private final UsuarioServicio servicio;

    public UsuarioControlador(UsuarioServicio servicio) {
        this.servicio = servicio;
    }

    /**
     * Realiza el inicio de sesión para un usuario.
     *
     * @param request HttpServletRequest con los parámetros "email" y "pass" que representan el email y contraseña del usuario.
     * @return Respuesta genérica que contiene el usuario logueado.
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<Usuario> login(HttpServletRequest request) {
        String email = request.getParameter("email");
        String contra = request.getParameter("pass");
        return this.servicio.login(email, contra);
    }

    /**
     * Guarda un nuevo usuario.
     *
     * @param u Usuario a ser guardado.
     * @return Respuesta genérica que indica el resultado de la operación de guardado.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse save(@RequestBody Usuario u) {
        return this.servicio.guardarUsuario(u);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id ID del usuario a ser actualizado.
     * @param u  Usuario con los datos actualizados.
     * @return Respuesta genérica que indica el resultado de la operación de actualización.
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse update(@PathVariable int id, @RequestBody Usuario u) {
        return this.servicio.guardarUsuario(u);
    }
}
