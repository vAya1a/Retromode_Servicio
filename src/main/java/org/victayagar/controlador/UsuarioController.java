package org.victayagar.controlador;

import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.victayagar.entidad.Usuario;
import org.victayagar.servicio.UsuarioService;
import org.victayagar.utilidades.GenericResponse;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }
    @PostMapping("/login")
    public GenericResponse<Usuario> login(HttpServletRequest request){
        String email = request.getParameter("email");
        String contra = request.getParameter("pass");
        return this.service.login(email, contra);
    }
    @PostMapping
    public GenericResponse save(@RequestBody Usuario u){
        return this.service.guardarUsuario(u);
    }
    @PutMapping("/{id}")
    public GenericResponse update(@PathVariable int id, @RequestBody Usuario u){
        return this.service.guardarUsuario(u);
    }
}