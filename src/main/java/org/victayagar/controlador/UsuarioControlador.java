package org.victayagar.controlador;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.victayagar.entidad.Usuario;
import org.victayagar.servicio.UsuarioServicio;
import org.victayagar.utilidades.GenericResponse;

@RestController
@RequestMapping("api/usuario")
public class UsuarioControlador {
    private final UsuarioServicio servicio;

    public UsuarioControlador(UsuarioServicio servicio) {
        this.servicio = servicio;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<Usuario> login(HttpServletRequest request) {
        String email = request.getParameter("email");
        String contra = request.getParameter("pass");
        return this.servicio.login(email, contra);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse save(@RequestBody Usuario u) {
        return this.servicio.guardarUsuario(u);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse update(@PathVariable int id, @RequestBody Usuario u) {
        return this.servicio.guardarUsuario(u);
    }
}
