package org.victayagar.servicio;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.victayagar.repositorio.ProductoRepositorio;
import org.victayagar.utilidades.GenericResponse;

import static org.victayagar.utilidades.Global.*;

@Service
@Transactional
public class ProductoServicio {
    private final ProductoRepositorio repositorio;

    public ProductoServicio(ProductoRepositorio repositorio) {
        this.repositorio =  repositorio;
    }
    public GenericResponse listarProductosRecomendados(){
        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, this.repositorio.listarProductosRecomendados());
    }

    public GenericResponse listarProductosPorCategoria(int idC){
        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, this.repositorio.listarProductosPorCategoria(idC));
    }
}
