package ru.ifmo.pga.news.stats.test.support;

import com.rometools.rome.feed.synd.SyndFeed;
import ru.ifmo.pga.news.NewsService;
import ru.ifmo.pga.news.exception.NewsServiceException;
import ru.ifmo.pga.news.stats.impl.WordCounter;

import java.text.ParseException;

public class TestService implements NewsService {
    private final FeedWriter writer;
    private WordCounter wordCounter;
    private final String name;
    private final int countOfEntries;

    public TestService(String name, FeedWriter writer, int countOfEntries) {
        this.name = name;
        this.writer = writer;
        this.countOfEntries = countOfEntries;
    }

    @Override
    public SyndFeed getNews() throws NewsServiceException {
        try {
            FeedWriter.FeedStats stats = writer.getFeed(countOfEntries);
            wordCounter = stats.getWordCounter();
            return stats.getFeed();
        } catch (ParseException e) {
            throw new NewsServiceException(e);
        }
    }

    public WordCounter getWordCounter() {
        return wordCounter;
    }

    @Override
    public String getName() {
        return name;
    }
}
