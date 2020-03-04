package com.ExamenMercadoLibre.Mutant.Excepcion;

public class ServiceMutantException extends Exception {

    private String message;

    public ServiceMutantException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
