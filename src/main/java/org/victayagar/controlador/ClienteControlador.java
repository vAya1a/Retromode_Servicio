package org.victayagar.controlador;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.victayagar.entidad.Cliente;
import org.victayagar.servicio.ClienteServicio;
import org.victayagar.utilidades.GenericResponse;

/*
Dentro de la clase ClienteControlador:
El método "save" se utiliza para guardar un nuevo cliente y
devuelve una respuesta genérica que indica el resultado de la operación.
El método "update" actualiza un cliente existente utilizando su identificador
y los datos actualizados, también devuelve una respuesta genérica indicando el resultado.
*/


@RestController
@RequestMapping("api/cliente")
public class ClienteControlador {
    private final ClienteServicio servicio;

    /**
     * Constructor de la clase ClienteControlador.
     *
     * @param servicio Servicio de cliente utilizado para realizar operaciones relacionadas con los clientes.
     */

    public ClienteControlador(ClienteServicio servicio) {
        this.servicio = servicio;
    }


    /**
     * Guarda un cliente y devuelve una respuesta genérica.
     *
     * @param c Cliente a guardar.
     * @return Respuesta genérica que indica el resultado de la operación.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse save(@Valid @RequestBody Cliente c) {
        return this.servicio.save(c);
    }


    /**
     * Actualiza un cliente existente y devuelve una respuesta genérica.
     *
     * @param id Identificador del cliente a actualizar.
     * @param c Cliente con los datos actualizados.
     * @return Respuesta genérica que indica el resultado de la operación.
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse update(@PathVariable int id, @Valid @RequestBody Cliente c) {
        c.setId(id);
        return this.servicio.save(c);
    }

}
