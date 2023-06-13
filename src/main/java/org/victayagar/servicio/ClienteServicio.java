package org.victayagar.servicio;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.victayagar.entidad.Cliente;
import org.victayagar.repositorio.ClienteRepositorio;
import org.victayagar.utilidades.GenericResponse;

import java.util.Optional;

import static org.victayagar.utilidades.Global.*;

/*
En esta clase, se utiliza la anotación @Service para indicar que es un componente
de servicio gestionado por Spring. Además, se aplica la anotación @Transactional
para indicar que los métodos de este servicio deben ser ejecutados dentro de una transacción.

En el constructor se inyecta el repositorio correspondiente (ClienteRepositorio),
y se asigna al atributo repositorio.

El método save() se encarga de guardar o actualizar un cliente. Se verifica si
el cliente ya existe en la base de datos mediante el uso del método findById().
Luego, se realizan distintas validaciones y se devuelve una respuesta genérica
(GenericResponse) que indica el resultado de la operación. Se incluye un comentario que describe su funcionalidad.
*/
@Service
@Transactional
public class ClienteServicio {
    private final ClienteRepositorio repositorio;

    public ClienteServicio(ClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Guarda o actualiza un cliente.
     *
     * @param c Cliente a guardar o actualizar.
     * @return Respuesta genérica que indica el resultado de la operación.
     */
    public GenericResponse save(Cliente c) {
        Optional<Cliente> opt = this.repositorio.findById(c.getId());
        int idf = opt.isPresent() ? opt.get().getId() : 0;
        if (idf == 0) {
            if (repositorio.existByDoc(c.getNumDoc().trim()) == 1) {
                return new GenericResponse(TIPO_RESULT, RPTA_WARNING, "Lo sentimos: " +
                        "Ya existe un cliente con ese mismo numero de documento. " +
                        "Si el problema persiste comuniquese con el soporte técnico.", null);
            } else {
                // Guarda el cliente
                c.setId(idf);
                return new GenericResponse(TIPO_DATA, RPTA_OK, "El cliente se ha registrado correctamente", this.repositorio.save(c));
            }
        } else {
            // Actualiza el registro del cliente
            if (repositorio.existByDocForUpdate(c.getNumDoc().trim(), c.getId()) == 1) {
                return new GenericResponse(TIPO_RESULT, RPTA_WARNING, "Error: Ya existe un cliente con esos mismos datos," +
                        "verifique e inténtalo de nuevo", null);
            } else {
                // Actualiza el cliente
                c.setId(idf);
                return new GenericResponse(TIPO_DATA, RPTA_OK, "Los datos del cliente han sido actualizados", this.repositorio.save(c));
            }
        }
    }
}

