package ru.ifmo.pga.news.stats.impl;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import ru.ifmo.pga.news.NewsService;
import ru.ifmo.pga.news.exception.NewsServiceException;
import ru.ifmo.pga.news.stats.NewsStats;
import ru.ifmo.pga.news.stats.exception.NewsStatsException;
import ru.ifmo.pga.news.stats.impl.utils.WordCounter;

import java.util.Arrays;
import java.util.List;


@Component(
        service = NewsStats.class,
        scope = ServiceScope.PROTOTYPE
)
@ServiceVendor("Pushkarev Gleb")
@ServiceDescription("Displays the top n words in news titles.")
public class NewsStatsImpl implements NewsStats {

    protected final WordCounter wordCount = new WordCounter();

    @Override
    public void clear() {
        wordCount.clear();
    }

    @Override
    public List<String> getStats(int count) {
        return wordCount.getStats(count);
    }

    @Override
    public void addNews(NewsService newsService) throws NewsStatsException {
        if (newsService == null) {
            throw new NewsStatsException("News Service is Null");
        }
        try {
            SyndFeed feed = newsService.getNews();
            if (feed == null) {
                throw new NewsStatsException("News is Null");
            }
            feed.getEntries()
                    .stream()
                    .map(SyndEntry::getTitle)
                    .flatMap(title -> Arrays.stream(title.split("[^A-Za-zА-Яа-я]+")))
                    .map(String::toLowerCase)
                    .forEach(wordCount::addWord);
        } catch (NewsServiceException e) {
            throw new NewsStatsException("Can't get news: " + e.getMessage(), e);
        }

    }
}
