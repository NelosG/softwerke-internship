package ru.ifmo.pga.news.stats.exception;

public class NewsStatsCommandException extends Exception {
    public NewsStatsCommandException() {
        super();
    }

    public NewsStatsCommandException(String message) {
        super(message);
    }

    public NewsStatsCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewsStatsCommandException(Throwable cause) {
        super(cause);
    }
}
