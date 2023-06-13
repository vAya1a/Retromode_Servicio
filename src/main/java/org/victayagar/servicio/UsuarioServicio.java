package org.victayagar.servicio;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.victayagar.entidad.Usuario;
import org.victayagar.repositorio.UsuarioRepositorio;
import org.victayagar.utilidades.GenericResponse;

import java.util.Optional;

import static org.victayagar.utilidades.Global.*;

/*En esta clase, se utiliza la anotación @Service para indicar que es un
componente de servicio gestionado por Spring.

En el constructor se inyecta el repositorio necesario.

El método login() se utiliza para realizar el inicio de sesión de un usuario. Recibe
como parámetros el correo electrónico y la contraseña del usuario. Se realiza una
consulta en el repositorio para verificar si existe un usuario con esas credenciales.
Si se encuentra un usuario válido, se devuelve como parte de una respuesta genérica
con un mensaje de éxito. En caso contrario, se devuelve una respuesta genérica con un
mensaje de advertencia indicando que el usuario no existe.

El método guardarUsuario() se utiliza para guardar los datos de un usuario. Recibe
como parámetro un objeto de tipo Usuario que se desea guardar. Se verifica si el usuario
ya existe en la base de datos buscando su ID. Si el ID es 0, significa que el usuario es
nuevo y se guarda en la base de datos. Si el ID no es 0, significa que el usuario ya existe
y se actualizan sus datos en la base de datos. Se devuelve una respuesta genérica con un
mensaje de éxito y el usuario guardado o actualizado.
*/

@Service
@Transactional
public class UsuarioServicio {
    private final UsuarioRepositorio repositorio;

    public UsuarioServicio(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Realiza el inicio de sesión de un usuario.
     *
     * @param email  Correo electrónico del usuario.
     * @param contra Contraseña del usuario.
     * @return Respuesta genérica con el usuario que ha iniciado sesión.
     */
    public GenericResponse<Usuario> login(String email, String contra) {
        Optional<Usuario> optU = this.repositorio.login(email, contra);
        if (optU.isPresent()) {
            return new GenericResponse<Usuario>(TIPO_AUTH, RPTA_OK, "Has iniciado sesión correctamente", optU.get());
        } else {
            return new GenericResponse<Usuario>(TIPO_AUTH, RPTA_WARNING, "Lo sentimos, ese usuario no existe", new Usuario());
        }
    }

    /**
     * Guarda los datos de un usuario.
     *
     * @param u Usuario a guardar.
     * @return Respuesta genérica con el usuario guardado o actualizado.
     */
    public GenericResponse guardarUsuario(Usuario u) {
        Optional<Usuario> optU = this.repositorio.findById(u.getId());
        int idf = optU.isPresent() ? optU.get().getId() : 0;
        if (idf == 0) {
            return new GenericResponse(TIPO_DATA, RPTA_OK, "El usuario se ha registrado correctamente", this.repositorio.save(u));
        } else {
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Los datos del usuario han sido actualizados", this.repositorio.save(u));
        }
    }
}