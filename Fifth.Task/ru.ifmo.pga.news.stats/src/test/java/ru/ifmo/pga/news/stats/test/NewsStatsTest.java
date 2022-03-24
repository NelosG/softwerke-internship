package ru.ifmo.pga.news.stats.test;


import org.junit.jupiter.api.Test;
import ru.ifmo.pga.news.stats.impl.exception.NewsStatsException;
import ru.ifmo.pga.news.stats.impl.utils.WordCounter;
import ru.ifmo.pga.news.stats.test.support.FeedWriter;
import ru.ifmo.pga.news.stats.test.support.TestService;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewsStatsTest extends NewsStatsBaseTest {
    protected final int COUNT_OF_SERVICES = 1000;
    protected final int COUNT_OF_WORDS = 10;


    @Test
    void serviceTest() throws NewsStatsException, NoSuchFieldException, IllegalAccessException {
        TestService service = new TestService(
                "testService1",
                new FeedWriter(FeedWriter.FEED_TYPE.RSS_2_0),
                COUNT_OF_ENTRIES
        );
        stats.bind(service);
        stats.stats("all");
        checkOutput(
                service
        );
        outContent.reset();
    }

    @Test
    void multipleServicesTest() throws NewsStatsException, NoSuchFieldException, IllegalAccessException {
        TestService service1 = new TestService(
                "testService1",
                new FeedWriter(FeedWriter.FEED_TYPE.RSS_2_0),
                COUNT_OF_ENTRIES
        );

        TestService service2 = new TestService(
                "testService2",
                new FeedWriter(FeedWriter.FEED_TYPE.RSS_2_0),
                COUNT_OF_ENTRIES
        );
        stats.bind(service1);
        stats.bind(service2);
        stats.stats("all");
        checkOutput(
                service1,
                service2
        );
    }

    @Test
    void randomServiceTest() throws NewsStatsException, NoSuchFieldException, IllegalAccessException {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < COUNT_OF_SERVICES; i++) {

            TestService service = new TestService(
                    "testService" + i,
                    new FeedWriter(FeedWriter.FEED_TYPE
                            .values()[
                            random.nextInt(FeedWriter.FEED_TYPE.values().length)
                            ]),
                    COUNT_OF_ENTRIES
            );
            stats.bind(service);
            stats.stats("all");
            checkOutput(service);
            stats.unbind(service);
            outContent.reset();
        }
    }

    private void checkOutput(TestService... services) throws NoSuchFieldException, IllegalAccessException {
        WordCounter wordCounter = new WordCounter();

        Field field = WordCounter.class.getDeclaredField("wordCount");
        field.setAccessible(true);
        field.set(wordCounter,
                collectMaps(services));

        Scanner sc = new Scanner(outContent.toString());
        List<String> actual = new ArrayList<>();
        while (sc.hasNext()) {
            actual.add(sc.next());
        }
        assertEquals(new HashSet<>(wordCounter.getMostPopularWords(COUNT_OF_WORDS)),
                new HashSet<>(actual));
    }

    private Map<String, Integer> collectMaps(TestService... services) throws NoSuchFieldException, IllegalAccessException {
        Field field = WordCounter.class.getDeclaredField("wordCount");
        field.setAccessible(true);
        Map<String, Integer> resMap = new TreeMap<>();
        for (TestService service : services) {
            @SuppressWarnings("unchecked")
            Map<String, Integer> wordCount = (Map<String, Integer>) field.get(service.getWordCounter());

            wordCount.forEach(
                    (key, value) -> resMap.merge(key, value, Integer::sum));
        }
        return resMap;
    }
}
