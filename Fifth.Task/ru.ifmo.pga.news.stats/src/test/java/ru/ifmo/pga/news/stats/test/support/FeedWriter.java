package ru.ifmo.pga.news.stats.test.support;

import com.rometools.rome.feed.synd.*;
import ru.ifmo.pga.news.stats.impl.utils.WordCounter;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FeedWriter {

    private static final DateFormat DATE_PARSER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static final List<String> FEED_TYPE_MAP;

    static {
        FEED_TYPE_MAP = new ArrayList<>();
        FEED_TYPE_MAP.add("rss_0.90");
        FEED_TYPE_MAP.add("rss_0.91");
        FEED_TYPE_MAP.add("rss_0.92");
        FEED_TYPE_MAP.add("rss_0.93");
        FEED_TYPE_MAP.add("rss_0.94");
        FEED_TYPE_MAP.add("rss_1.0");
        FEED_TYPE_MAP.add("rss_2.0");
        FEED_TYPE_MAP.add("atom_0.3");
    }

    private int feedType = 0;

    public FeedWriter(FEED_TYPE type) {
        if (type != null) {
            feedType = type.ordinal();
        }
    }

    private static Date getDate() throws ParseException {
        return DATE_PARSER.parse("02.02.2022 02:02:02");
    }

    public FeedStats getFeed(int countOfEntries) throws ParseException {
        SyndFeed feed = new SyndFeedImpl();
        WordCounter wordCounter = new WordCounter();

        feed.setFeedType(FEED_TYPE_MAP.get(feedType));

        feed.setTitle("Test Feed");
        feed.setLink("https://github.com/NelosG/softwerke-internship");
        feed.setAuthor("NelosG");
        feed.setCopyright("ru.ifmo.pga");
        feed.setPublishedDate(getDate());
        feed.setDescription("Test feed for testing news:stats");
        List<SyndEntry> entries = new ArrayList<>();

        for (int i = 0; i < countOfEntries; i++) {
            entries.add(getEntry(wordCounter));
        }


        feed.setEntries(entries);
        return new FeedStats(feed, wordCounter);
    }


    private SyndEntry getEntry(WordCounter wordCounter) throws ParseException {
        SyndEntry entry;

        entry = new SyndEntryImpl();
        entry.setTitle(generateTitle(wordCounter));
        entry.setLink("https://github.com/NelosG");
        entry.setPublishedDate(getDate());

        SyndContent description = new SyndContentImpl();
        description.setType("text/plain");
        description.setValue("Initial release of ROME");
        entry.setDescription(description);
        return entry;
    }

    private String generateTitle(WordCounter wordCounter) {
        Random random = new Random();
        try {
            if (random.nextInt(2) == 0 && !wordCounter.isEmpty()) {
                int index = random.nextInt(wordCounter.size());
                Field field = WordCounter.class.getDeclaredField("wordCount");
                int i = 0;
                @SuppressWarnings("unchecked")
                Map<String, Integer> map = (Map<String, Integer>) field.get(wordCounter);
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    if (i == index) {
                        wordCounter.addWord(entry.getKey());
                        return entry.getKey();
                    }
                    i++;
                }
            }
        } catch (Exception ignored) {
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < random.nextInt(6) + 5; i++) {
            sb.append((char) ('a' + random.nextInt(26)));
        }
        String s = sb.toString();
        wordCounter.addWord(s);
        return s;
    }

    public enum FEED_TYPE {
        RSS_0_90,
        RSS_0_91,
        RSS_0_92,
        RSS_0_93,
        RSS_0_94,
        RSS_1_0,
        RSS_2_0,
        ATOM_0_3
    }

    public static class FeedStats {
        private final WordCounter wordCounter;
        private final SyndFeed feed;

        public FeedStats(SyndFeed feed, WordCounter wordCounter) {
            this.wordCounter = wordCounter;
            this.feed = feed;
        }

        public WordCounter getWordCounter() {
            return wordCounter;
        }

        public SyndFeed getFeed() {
            return feed;
        }
    }
}
