package com.ExamenMercadoLibre.Mutant.Excepcion;

public class InvalidDataReceivedException extends Exception {

    private String message;

    public InvalidDataReceivedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
