package org.victayagar.utilidades;

/*
La clase GenericResponse es una respuesta genérica que se utiliza para
enviar respuestas estandarizadas desde los servicios. Contiene campos
como type (tipo de la respuesta), rpta (respuesta), message (mensaje de la respuesta)
y body (cuerpo de la respuesta) para proporcionar información detallada sobre la respuesta.
Permite enviar respuestas consistentes y estructuradas desde los servicios hacia los clientes.
*/
public class GenericResponse<T> {
    private String type;
    private int rpta;
    private String message;
    private T body;

    public GenericResponse() {
        type = "";
        rpta = 0;
        message = "";
        body = null;
    }

    public GenericResponse(String bodyType, Object body) {
        type = "";
        rpta = 0;
        message = "";
        this.body = null;
    }

    public GenericResponse(String type, int rpta, String message, T body) {
        this.type = type;
        this.rpta = rpta;
        this.message = message;
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRpta() {
        return rpta;
    }

    public void setRpta(int rpta) {
        this.rpta = rpta;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}