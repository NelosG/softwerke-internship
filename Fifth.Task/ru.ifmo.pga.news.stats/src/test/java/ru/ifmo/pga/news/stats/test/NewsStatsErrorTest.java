package ru.ifmo.pga.news.stats.test;


import com.rometools.rome.feed.synd.SyndFeed;
import org.junit.jupiter.api.Test;
import ru.ifmo.pga.news.stats.impl.exception.NewsStatsException;
import ru.ifmo.pga.news.stats.test.support.TestService;
import ru.ifmo.pga.news.stats.test.support.FeedWriter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class NewsStatsErrorTest extends NewsStatsBaseTest{

    @Test
    void errorNullTest() {
        try {
            stats.bind(null);

        }catch (NewsStatsException e) {
            assertEquals("Can't bind news service because it's null", e.getMessage());
            return;
        }
        fail("Expected NewsStatsException with message:\n"+
                "Can't bind news service because it's null");
    }

    @Test
    void errorNameTest() {
        try {


        TestService service = new TestService(
                "testService",
                new FeedWriter(FeedWriter.FEED_TYPE.RSS_2_0),
                COUNT_OF_ENTRIES
        ) {
            @Override
            public String getName() {
                return null;
            }
        };
        stats.bind(service);
        stats.stats(service.getName());
        assertEquals(
                "Can't get news from: " + service.getName() + " newsService" + System.lineSeparator() +
                        "Cause: News is Null" + System.lineSeparator(),
                errContent.toString());
        errContent.reset();
        }catch (NewsStatsException e) {
            assertEquals("Can't bind news service because its name is null", e.getMessage());
            return;
        }
        fail("Expected NewsStatsException with message:\n"+
                "Can't bind news service because its name is null");
    }

    @Test
    void errorNewsTest() throws NewsStatsException {
        TestService service = new TestService(
                "testService1",
                new FeedWriter(FeedWriter.FEED_TYPE.RSS_2_0),
                COUNT_OF_ENTRIES
        ) {
            @Override
            public SyndFeed getNews() {
                return null;
            }
        };
        stats.bind(service);
        stats.stats(service.getName());
        assertEquals(
                "Can't get news from: testService1 newsService" + System.lineSeparator() +
                        "Cause: News is Null" + System.lineSeparator(),
                errContent.toString());
        errContent.reset();
    }
}
