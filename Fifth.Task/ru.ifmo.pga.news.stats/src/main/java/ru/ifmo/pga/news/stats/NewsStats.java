package ru.ifmo.pga.news.stats;

import ru.ifmo.pga.news.NewsService;
import ru.ifmo.pga.news.stats.exception.NewsStatsException;

import java.util.List;

public interface NewsStats {
    void clear();

    List<String> getStats(int count);

    void addNews(NewsService newsService) throws NewsStatsException;
}
