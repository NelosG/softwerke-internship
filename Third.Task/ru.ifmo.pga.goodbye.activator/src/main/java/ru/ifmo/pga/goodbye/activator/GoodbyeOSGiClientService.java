package ru.ifmo.pga.goodbye.activator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import ru.ifmo.pga.goodbye.service.GoodbyeOSGi;

@Component
@ServiceVendor("Pushkarev Gleb")
@ServiceDescription("Provides a friendly farewell on activation.")
public class GoodbyeOSGiClientService {

    @Reference(service = GoodbyeOSGi.class)
    private GoodbyeOSGi goodbyeOSGi;

    @Activate
    public void activate() {
        goodbyeOSGi.goodbye();
    }
}
