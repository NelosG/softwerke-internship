package ru.ifmo.pga.news.stats.test;


import org.junit.jupiter.api.Test;
import ru.ifmo.pga.news.stats.impl.exception.NewsStatsException;
import ru.ifmo.pga.news.stats.test.support.FeedWriter;
import ru.ifmo.pga.news.stats.test.support.TestService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewsStatsServiceBindingTest extends NewsStatsBaseTest {

    @Test
    void serviceBindingTest() throws NewsStatsException {
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
        stats.stats();
        assertEquals(createMessage(
                        service1
                ),
                outContent.toString());
        outContent.reset();

        stats.bind(service1);
        stats.stats();
        assertEquals(createMessage(
                        service1
                ),
                outContent.toString());
        outContent.reset();

        stats.bind(service2);
        stats.stats();
        assertEquals(createMessage(
                service1,
                service2
        ), outContent.toString());
        outContent.reset();

        stats.unbind(service1);
        stats.stats();
        assertEquals(createMessage(
                        service2
                ),
                outContent.toString());
    }

    private String createMessage(TestService... services) {
        StringBuilder sb = new StringBuilder();

        sb.append("Please specify newsService.").append(System.lineSeparator());
        sb.append("Now available: {").append(System.lineSeparator());

        for (TestService service : services) {
            sb.append("\t").append(service.getName()).append(System.lineSeparator());
        }
        sb.append("}").append(System.lineSeparator());
        sb.append("Or you can type \"all\" to use all services").append(System.lineSeparator());

        return sb.toString();
    }
}
