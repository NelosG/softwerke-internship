package ru.ifmo.pga.news;

import com.rometools.rome.feed.synd.SyndFeed;
import ru.ifmo.pga.news.exception.NewsServiceException;

public interface NewsService {
    SyndFeed getNews() throws NewsServiceException;

    String getName();
}
