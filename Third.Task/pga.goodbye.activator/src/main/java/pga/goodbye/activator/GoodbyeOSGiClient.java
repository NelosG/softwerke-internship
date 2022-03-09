package pga.goodbye.activator;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.*;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import pga.goodbye.service.GoodbyeOSGi;


@Component
@ServiceVendor("Pushkarev Gleb")
@ServiceDescription("Provides a friendly farewell on activation.")
public class GoodbyeOSGiClient {

    @Reference(service = GoodbyeOSGi.class)
    private GoodbyeOSGi goodbyeOSGi;

    @Activate
    public void activate() {
        goodbyeOSGi.goodbye();
    }

    @Deactivate
    public void deactivate() {
    }
}
