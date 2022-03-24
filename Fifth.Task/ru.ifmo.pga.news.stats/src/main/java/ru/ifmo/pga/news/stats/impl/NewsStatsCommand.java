package ru.ifmo.pga.news.stats.impl;


import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.osgi.service.component.annotations.*;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import ru.ifmo.pga.news.NewsService;
import ru.ifmo.pga.news.exception.NewsServiceException;
import ru.ifmo.pga.news.stats.NewsStats;
import ru.ifmo.pga.news.stats.impl.exception.NewsStatsException;

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

    protected final Set<String> IGNORED_WORDS;

    protected final int COUNT_OF_WORDS = 10;

    {
        IGNORED_WORDS = new HashSet<>();
        IGNORED_WORDS.add("а");
        IGNORED_WORDS.add("за");
        IGNORED_WORDS.add("из");
        IGNORED_WORDS.add("на");
        IGNORED_WORDS.add("под");
        IGNORED_WORDS.add("с");
        IGNORED_WORDS.add("о");
        IGNORED_WORDS.add("без");
        IGNORED_WORDS.add("до");
        IGNORED_WORDS.add("к");
        IGNORED_WORDS.add("по");
        IGNORED_WORDS.add("от");
        IGNORED_WORDS.add("перед");
        IGNORED_WORDS.add("при");
        IGNORED_WORDS.add("через");
        IGNORED_WORDS.add("у");
        IGNORED_WORDS.add("над");
        IGNORED_WORDS.add("об");
        IGNORED_WORDS.add("про");
        IGNORED_WORDS.add("для");
        IGNORED_WORDS.add("в");
        IGNORED_WORDS.add("и");
        IGNORED_WORDS.add("не");
    }

    @Reference(
            service = NewsService.class,
            cardinality = ReferenceCardinality.AT_LEAST_ONE,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unbind"
    )
    public void bind(NewsService mediaPortal) throws NewsStatsException {
        if(mediaPortal == null) {
            throw new NewsStatsException("Can't bind news service because it's null");
        }
        if(mediaPortal.getName() == null) {
            throw new NewsStatsException("Can't bind news service because its name is null");
        }
        mediaPortalMap.put(mediaPortal.getName(), mediaPortal);
    }

    public void unbind(NewsService mediaPortal) {
        mediaPortalMap.remove(mediaPortal.getName());
    }

    //TODO:write description
    @Override
    public void stats(String... newsServices) {
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
        printStats(new TreeSet<>(Arrays.asList(newsServices)));
    }

    protected void printStats(Set<String> newsServices) {
        WordCounter wordCount = new WordCounter();
        if (newsServices == null) {
            countWords(wordCount, s -> true);
        } else {
            countWords(wordCount, newsServices::contains);
        }
        List<String> list = wordCount.getMostPopularWords();
        for (int i = 0; i < list.size() && i < COUNT_OF_WORDS; ++i) {
            System.out.println(list.get(i));
        }
    }

    protected void countWords(final WordCounter wordCount,
                              Predicate<String> predicate) {
        mediaPortalMap.entrySet().stream()
                .filter(entry -> predicate.test(entry.getKey()))
                .map(Map.Entry::getValue)
                .forEach(newsService -> {
                    try {
                        SyndFeed feed = newsService.getNews();
                        if(feed == null) {
                            throw new NewsServiceException("News is Null");
                        }
                                feed.getEntries()
                                .stream()
                                .map(SyndEntry::getTitle)
                                .flatMap(title -> Arrays.stream(title.split("\\s+")))
                                .map(String::toLowerCase)
                                .filter(s -> !IGNORED_WORDS.contains(s))
                                .forEach(wordCount::addWord);
                    } catch (NewsServiceException e) {
                        System.err.println("Can't get news from: " + newsService.getName() + " newsService");
                        System.err.println("Cause: " + e.getMessage());
                    }
                });
    }
}
