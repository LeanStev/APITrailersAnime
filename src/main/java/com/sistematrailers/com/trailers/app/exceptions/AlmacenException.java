package com.sistematrailers.com.trailers.app.exceptions;


public class AlmacenException extends RuntimeException{

    public AlmacenException(String mensaje){
        super(mensaje);
    }
    public AlmacenException(String mensaje, Throwable exception){
        super(mensaje,exception);
    }
}
