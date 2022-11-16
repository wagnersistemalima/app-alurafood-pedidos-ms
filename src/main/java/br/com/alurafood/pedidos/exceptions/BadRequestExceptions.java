package br.com.alurafood.pedidos.exceptions;

public class BadRequestExceptions extends RuntimeException {

    public BadRequestExceptions(String message) {
        super(message);
    }
}
