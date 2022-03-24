package ru.ifmo.pga.news.aif;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.osgi.service.component.annotations.Component;
import ru.ifmo.pga.news.NewsService;
import ru.ifmo.pga.news.exception.NewsServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;


@Component(
        service = NewsService.class,
        immediate = true
)
public class AifNewsServise implements NewsService {
    @Override
    public SyndFeed getNews() throws NewsServiceException {
        try {
            URL url = new URL("https://aif.ru/rss/news.php");
            XmlReader reader = new XmlReader(url);
            return new SyndFeedInput(false, new Locale("ru")).build(reader);
        } catch (IOException | FeedException e) {
            throw new NewsServiceException(e);
        }

    }

    @Override
    public String getName() {
        return "aif";
    }
}
