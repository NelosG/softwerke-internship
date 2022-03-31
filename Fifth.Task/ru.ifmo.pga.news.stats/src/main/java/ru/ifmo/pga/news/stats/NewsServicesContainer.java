package ru.ifmo.pga.news.stats;

import ru.ifmo.pga.news.NewsService;

import java.util.Collection;

public interface NewsServicesContainer {
    boolean isEmpty();

    Collection<NewsService> allServices();

    NewsService getService(String newsService);
}
