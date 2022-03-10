package ru.ifmo.pga.hello.activator.exception;

public class HelloOSGiActivatorException extends Exception {
    public HelloOSGiActivatorException() {
        super();
    }

    public HelloOSGiActivatorException(String message) {
        super(message);
    }

    public HelloOSGiActivatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public HelloOSGiActivatorException(Throwable cause) {
        super(cause);
    }
}
