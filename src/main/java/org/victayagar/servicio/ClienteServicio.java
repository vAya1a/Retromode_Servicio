package org.victayagar.servicio;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.victayagar.entidad.Cliente;
import org.victayagar.repositorio.ClienteRepositorio;
import org.victayagar.utilidades.GenericResponse;

import java.util.Optional;

import static org.victayagar.utilidades.Global.*;

@Service
@Transactional
public class ClienteServicio {
    private final ClienteRepositorio repositorio;

    public ClienteServicio(ClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    //Metodo para guardar y actualizar cliente
    public GenericResponse save(Cliente c) {
        Optional<Cliente> opt = this.repositorio.findById(c.getId());
        int idf = opt.isPresent() ? opt.get().getId() : 0;
        if (idf == 0) {
            if (repositorio.existByDoc(c.getNumDoc().trim()) == 1) {
                return new GenericResponse(TIPO_RESULT, RPTA_WARNING, "Lo sentimos: " +
                        "Ya existe un cliente con ese mismo numero de documento. " +
                        "Si el problema persiste comuniquese con el soporte técnico.", null);
            } else {
                //Guarda
                c.setId(idf);
                return new GenericResponse(TIPO_DATA, RPTA_OK, "El cliente se ha registrado correctamente", this.repositorio.save(c));
            }
        } else {
            //Actualizar registro
            if (repositorio.existByDocForUpdate(c.getNumDoc().trim(), c.getId()) == 1) {
                return new GenericResponse(TIPO_RESULT, RPTA_WARNING, "Error: Ya existe un cliente con esos mismos datos," +
                        "verifique e inténtalo de nuevo", null);
            } else {
                //Actualiza
                c.setId(idf);
                return new GenericResponse(TIPO_DATA, RPTA_OK, "Los datos del cliente han sido actualizados", this.repositorio.save(c));
            }
        }
    }
}

