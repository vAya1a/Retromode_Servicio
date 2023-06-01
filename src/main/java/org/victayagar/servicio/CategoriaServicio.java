package org.victayagar.servicio;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.victayagar.repositorio.CategoriaRepositorio;
import org.victayagar.utilidades.GenericResponse;

import static org.victayagar.utilidades.Global.*;

@Service
@Transactional
public class CategoriaServicio {
    private final CategoriaRepositorio repositorio;

    public CategoriaServicio(CategoriaRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    public GenericResponse listarCategoriasActivas(){
        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, this.repositorio.listarCategoriasActivas());
    }
}
