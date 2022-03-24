package ru.ifmo.pga.news.stats.impl.exception;

public class NewsStatsException extends Exception {
    public NewsStatsException() {
        super();
    }

    public NewsStatsException(String message) {
        super(message);
    }

    public NewsStatsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewsStatsException(Throwable cause) {
        super(cause);
    }
}
