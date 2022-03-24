package test;

import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.SyndFeedOutput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedWriter {

    private static final DateFormat DATE_PARSER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static final List<String> FEED_TYPE;

    static {
        FEED_TYPE = new ArrayList<>();
        FEED_TYPE.add("rss_0.90");
        FEED_TYPE.add("rss_0.91");
        FEED_TYPE.add("rss_0.92");
        FEED_TYPE.add("rss_0.93");
        FEED_TYPE.add("rss_0.94");
        FEED_TYPE.add("rss_1.0");
        FEED_TYPE.add("rss_2.0");
        FEED_TYPE.add("atom_0.3");
    }

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://api.lenta.ru/rss");
//        URL url  = new URL("https://aif.ru/rss/news.php");
//        URL url  = new URL("https://www.vedomosti.ru/rss/news");


        try (XmlReader reader = new XmlReader(url)) {

            SyndFeed feed = new SyndFeedInput().build(reader);

            for (SyndEntry entry : feed.getEntries()) {
                System.out.println(entry.getTitle());
            }
        } catch (IOException | FeedException e) {
            e.printStackTrace();
        }

    }

    private static SyndEntry getEntry() throws ParseException {
        SyndEntry entry;
        SyndContent description;

        entry = new SyndEntryImpl();
        entry.setTitle("Test");
        entry.setLink("https://github.com/NelosG");
        entry.setPublishedDate(getDate());
        description = new SyndContentImpl();
        description.setType("text/plain");
        description.setValue("Initial release of ROME");
        entry.setDescription(description);
        return entry;
    }

    private static Date getDate() throws ParseException {
        return DATE_PARSER.parse("02.02.2022 02:02:02");
    }

    private void test() {

        boolean ok = false;
        try {
            SyndFeed feed = new SyndFeedImpl();

            feed.setFeedType(FEED_TYPE.get(FEED_TYPE.size() - 2));

            feed.setTitle("Test Feed");
            feed.setLink("https://github.com/NelosG/softwerke-internship");
            feed.setAuthor("NelosG");
            feed.setCopyright("ru.ifmo.pga");
            feed.setPublishedDate(getDate());
            feed.setDescription("Test feed for testing news:stats");
            List<SyndEntry> entries = new ArrayList<>();

            //TODO: write test generation
            for (int i = 0; i < 1; i++) {
                entries.add(getEntry());

            }


            feed.setEntries(entries);

            SyndFeedOutput output = new SyndFeedOutput();
            System.out.println(output.outputString(feed));

            ok = true;
        } catch (Exception ignored) {
        }

        if (!ok) {
            System.err.println("Filed to generate RSS.");
        }
    }

}
