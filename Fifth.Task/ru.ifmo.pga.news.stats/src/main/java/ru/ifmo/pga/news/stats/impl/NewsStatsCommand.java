package ru.ifmo.pga.news.stats.impl;


import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import ru.ifmo.pga.news.NewsService;
import ru.ifmo.pga.news.exception.NewsServiceException;
import ru.ifmo.pga.news.stats.NewsStats;
import ru.ifmo.pga.news.stats.impl.exception.NewsStatsException;
import ru.ifmo.pga.news.stats.impl.utils.WordCounter;

import java.util.*;
import java.util.function.Predicate;

@Component(
        service = NewsStats.class,
        immediate = true,
        property = {
                "osgi.command.scope=news",
                "osgi.command.function=stats"
        }
)
@ServiceVendor("Pushkarev Gleb")
@ServiceDescription("Provides a friendly farewell.")
public class NewsStatsCommand implements NewsStats {

    protected final Map<String, NewsService> mediaPortalMap = new TreeMap<>();
    protected final int COUNT_OF_WORDS = 10;


    @Reference(
            service = NewsService.class,
            cardinality = ReferenceCardinality.MULTIPLE,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unbind"
    )
    public void bind(NewsService mediaPortal) throws NewsStatsException {
        if (mediaPortal == null) {
            throw new NewsStatsException("Can't bind news service because it's null");
        }
        if (mediaPortal.getName() == null) {
            throw new NewsStatsException("Can't bind news service because its name is null");
        }
        mediaPortalMap.put(mediaPortal.getName(), mediaPortal);
    }

    public void unbind(NewsService mediaPortal) {
        mediaPortalMap.remove(mediaPortal.getName());
    }

    @Override
    public void stats(String... newsServices) {
        if (mediaPortalMap.isEmpty()) {
            System.out.println("No service is currently available");
            return;
        }
        if (newsServices.length == 0) {
            System.out.println("Please specify newsService.");
            System.out.println("Now available: {");
            mediaPortalMap.keySet().forEach(s -> System.out.println("\t" + s));
            System.out.println("}");
            System.out.println("Or you can type \"all\" to use all services");
            return;
        }
        if (newsServices.length == 1 && newsServices[0].equalsIgnoreCase("all")) {
            printStats(null);
            return;
        }
        printStats(new HashSet<>(Arrays.asList(newsServices)));
    }

    protected void printStats(Set<String> newsServices) {
        WordCounter wordCount = new WordCounter();
        if (newsServices == null) {
            countWords(wordCount, s -> true);
        } else {
            for (String newsService : newsServices) {
                if (!mediaPortalMap.containsKey(newsService)) {
                    System.err.println("Can't get news from: " + newsService + " newsService");
                    System.err.println("Cause: Service unavailable");
                }
            }
            countWords(wordCount, newsServices::contains);
        }
        wordCount.getMostPopularWords(COUNT_OF_WORDS).forEach(System.out::println);
    }

    protected void countWords(final WordCounter wordCount,
                              Predicate<String> predicate) {
        mediaPortalMap.entrySet().stream()
                .filter(entry -> predicate.test(entry.getKey()))
                .map(Map.Entry::getValue)
                .forEach(newsService -> {
                    try {
                        SyndFeed feed = newsService.getNews();
                        if (feed == null) {
                            System.err.println("Can't get news from: " + newsService.getName() + " newsService");
                            System.err.println("Cause: News is Null");
                            return;
                        }
                        feed.getEntries()
                                .stream()
                                .map(SyndEntry::getTitle)
                                .flatMap(title -> Arrays.stream(title.split("\\s+")))
                                .map(String::toLowerCase)
                                .forEach(wordCount::addWord);
                    } catch (NewsServiceException e) {
                        System.err.println("Can't get news from: " + newsService.getName() + " newsService");
                        System.err.println("Cause: " + e.getMessage());
                    }
                });
    }
}
