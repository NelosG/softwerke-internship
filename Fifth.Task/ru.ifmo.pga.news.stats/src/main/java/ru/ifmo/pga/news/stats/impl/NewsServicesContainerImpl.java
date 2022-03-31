package ru.ifmo.pga.news.stats.impl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import ru.ifmo.pga.news.NewsService;
import ru.ifmo.pga.news.stats.NewsServicesContainer;
import ru.ifmo.pga.news.stats.exception.NewsStatsCommandException;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

@Component(
        service = NewsServicesContainer.class,
        immediate = true
)
@ServiceVendor("Pushkarev Gleb")
@ServiceDescription("Displays the top n words in news titles.")
public class NewsServicesContainerImpl implements NewsServicesContainer {
    protected final Map<String, NewsService> mediaPortalMap = new TreeMap<>();

    @Reference(
            service = NewsService.class,
            cardinality = ReferenceCardinality.MULTIPLE,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unbind"
    )
    public void bind(NewsService mediaPortal) throws NewsStatsCommandException {
        if (mediaPortal == null) {
            throw new NewsStatsCommandException("Can't bind news service because it's null");
        }
        if (mediaPortal.getName() == null) {
            throw new NewsStatsCommandException("Can't bind news service because its name is null");
        }
        mediaPortalMap.put(mediaPortal.getName(), mediaPortal);
    }

    public void unbind(NewsService mediaPortal) {
        mediaPortalMap.remove(mediaPortal.getName());
    }

    @Override
    public boolean isEmpty() {
        return mediaPortalMap.isEmpty();
    }

    @Override
    public Collection<NewsService> allServices() {
        return mediaPortalMap.values();
    }

    @Override
    public NewsService getService(String newsService) {
        return mediaPortalMap.get(newsService);
    }
}
