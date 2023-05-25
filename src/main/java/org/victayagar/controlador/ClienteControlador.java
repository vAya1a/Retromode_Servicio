package org.victayagar.controlador;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.victayagar.entidad.Cliente;
import org.victayagar.servicio.ClienteServicio;
import org.victayagar.utilidades.GenericResponse;

@RestController
@RequestMapping("api/cliente")
public class ClienteControlador {
    private final ClienteServicio servicio;

    public ClienteControlador(ClienteServicio servicio) {
        this.servicio = servicio;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse save(@Valid @RequestBody Cliente c){
        return this.servicio.save(c);
    }

    @PutMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse update(@PathVariable int id, @Valid @RequestBody Cliente c){
        c.setId(id);
        return this.servicio.save(c);
    }

}
