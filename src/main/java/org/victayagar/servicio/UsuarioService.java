package org.victayagar.servicio;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.victayagar.entidad.Usuario;
import org.victayagar.repositorio.UsuarioRepositorio;
import org.victayagar.utilidades.GenericResponse;

import java.util.Optional;

import static org.victayagar.utilidades.Global.*;


@Service
@Transactional
public class UsuarioService {
    private final UsuarioRepositorio repositorio;

    public UsuarioService(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    //Método para iniciar sesión
    public GenericResponse<Usuario> login(String email, String contra){
        Optional<Usuario> optU = this.repositorio.login(email, contra);
        if(optU.isPresent()){
            return new GenericResponse<Usuario>(TIPO_AUTH, RPTA_OK, "Has iniciado sesión correctamente", optU.get());
        }else{
            return new GenericResponse<Usuario>(TIPO_AUTH, RPTA_WARNING, "Lo sentimos, ese usuario no existe", new Usuario());
        }
    }
    //Método para guardar credenciales del usuario
    public GenericResponse guardarUsuario(Usuario u){
        Optional<Usuario> optU = this.repositorio.findById(u.getId());
        int idf = optU.isPresent() ? optU.get().getId() : 0;
        if(idf == 0){
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Usuario Registrado Correctamente", this.repositorio.save(u));
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Datos del usuario actualizados", this.repositorio.save(u));
        }
    }
}