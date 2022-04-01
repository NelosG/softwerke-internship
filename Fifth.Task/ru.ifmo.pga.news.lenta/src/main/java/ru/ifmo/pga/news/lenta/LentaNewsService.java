package ru.ifmo.pga.news.lenta;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import ru.ifmo.pga.news.NewsService;
import ru.ifmo.pga.news.exception.NewsServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;


@Component(
        service = NewsService.class,
        immediate = true,
        configurationPolicy = ConfigurationPolicy.OPTIONAL
)
@Designate(
        ocd = LentaNewsService.LentaUrlConfig.class
)
@ServiceVendor("Pushkarev Gleb")
@ServiceDescription("Displays the top n words in news titles.")
public class LentaNewsService implements NewsService {

    protected String urlAddress = "";

    @Activate
    protected void activate(LentaUrlConfig configuration) {
        urlAddress = configuration.getUrl();
    }

    @Override
    public SyndFeed getNews() throws NewsServiceException {
        try {
            URL url = new URL(urlAddress);
            XmlReader reader = new XmlReader(url);
            return new SyndFeedInput(false, new Locale("ru")).build(reader);
        } catch (IOException | FeedException e) {
            throw new NewsServiceException(e);
        }

    }

    @Override
    public String getName() {
        return "lenta";
    }

    @ObjectClassDefinition(name = "NewsService:Lenta Url Config")
    protected @interface LentaUrlConfig {
        @AttributeDefinition(
                name = "Lenta Url Config",
                description = "Enter url for Lenta rss"
        )
        String getUrl() default "https://api.lenta.ru/rss";
    }
}
