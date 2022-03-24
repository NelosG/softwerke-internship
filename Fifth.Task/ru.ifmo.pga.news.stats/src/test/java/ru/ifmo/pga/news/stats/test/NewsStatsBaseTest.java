package ru.ifmo.pga.news.stats.test;

import org.junit.jupiter.api.BeforeEach;
import ru.ifmo.pga.news.stats.impl.NewsStatsCommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public abstract class NewsStatsBaseTest {
    protected final int COUNT_OF_ENTRIES = 100;

    protected ByteArrayOutputStream outContent;
    protected ByteArrayOutputStream errContent;
    protected NewsStatsCommand stats;

    @BeforeEach
    public void setUpStreams() {
        stats = new NewsStatsCommand();
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }
}
