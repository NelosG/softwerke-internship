package ru.ifmo.pga.news.exception;

public class NewsServiceException extends Exception {
    public NewsServiceException() {
        super();
    }

    public NewsServiceException(String message) {
        super(message);
    }

    public NewsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewsServiceException(Throwable cause) {
        super(cause);
    }
}
