package ru.ifmo.pga.news.stats.impl;


import org.osgi.service.component.annotations.*;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import ru.ifmo.pga.news.NewsService;
import ru.ifmo.pga.news.stats.NewsServicesContainer;
import ru.ifmo.pga.news.stats.NewsStats;
import ru.ifmo.pga.news.stats.NewsStatsCommand;
import ru.ifmo.pga.news.stats.exception.NewsStatsException;

import java.util.HashSet;
import java.util.Set;

@Component(
        service = NewsStatsCommand.class,
        immediate = true,
        property = {
                "osgi.command.scope=news",
                "osgi.command.function=stats"
        },
        configurationPolicy = ConfigurationPolicy.OPTIONAL
)
@Designate(
        ocd = NewsStatsCommandImpl.CountConfig.class
)
@ServiceVendor("Pushkarev Gleb")
@ServiceDescription("Displays the top n words in news titles.")
public class NewsStatsCommandImpl implements NewsStatsCommand {

    protected int COUNT_OF_WORDS;
    @Reference(
            service = NewsStats.class,
            scope = ReferenceScope.PROTOTYPE
    )
    protected NewsStats newsStats;
    @Reference(service = NewsServicesContainer.class)
    protected NewsServicesContainer servicesContainer;

    @Activate
    protected void activate(CountConfig countConfig) {
        COUNT_OF_WORDS = countConfig.getCount();
    }

    @Override
    public void stats(String... newsServices) {
        if (servicesContainer.isEmpty()) {
            System.out.println("No service is currently available");
            return;
        }
        if (newsServices.length == 0) {
            System.out.println("Please specify newsService.");
            System.out.println("Now available: {");
            servicesContainer.allServices()
                    .stream()
                    .map(NewsService::getName)
                    .forEach(s -> System.out.println("\t" + s));
            System.out.println("}");
            System.out.println("Or you can type \"all\" to use all services");
            return;
        }
        for (NewsService service : getServicesSet(newsServices)) {
            try {
                newsStats.addNews(service);
            } catch (NewsStatsException e) {
                System.err.println("Can't get news from: " + service.getName() + " newsService");
                System.err.println("Cause: " + e.getMessage());
            }
        }
        newsStats.getStats(COUNT_OF_WORDS).forEach(System.out::println);
        newsStats.clear();
    }

    protected Set<NewsService> getServicesSet(String[] newsServices) {
        Set<NewsService> servicesSet = new HashSet<>();
        if (newsServices.length == 1 && "all".equalsIgnoreCase(newsServices[0])) {
            servicesSet.addAll(servicesContainer.allServices());
        } else {
            for (String newsService : newsServices) {
                NewsService service = servicesContainer.getService(newsService);
                if (service == null) {
                    System.err.println("Can't get news from: " + newsService + " newsService");
                    System.err.println("Cause: Service unavailable");
                } else {
                    servicesSet.add(service);
                }
            }
        }
        return servicesSet;
    }

    @ObjectClassDefinition(name = "NewsStats:Count of words config")
    protected @interface CountConfig {
        @AttributeDefinition(
                name = "Count config",
                description = "Enter count of news to show",
                type = AttributeType.INTEGER
        )
        int getCount() default 10;
    }
}
