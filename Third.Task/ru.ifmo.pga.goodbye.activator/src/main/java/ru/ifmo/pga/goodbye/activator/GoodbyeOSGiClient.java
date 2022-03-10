package ru.ifmo.pga.goodbye.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import ru.ifmo.pga.goodbye.activator.exception.GoodbyeOSGiActivatorException;
import ru.ifmo.pga.goodbye.service.GoodbyeOSGi;


public class GoodbyeOSGiClient implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        ServiceReference<?> serviceRef = bundleContext.getServiceReference(GoodbyeOSGi.class.getName());
        if (serviceRef == null) {
            throw new GoodbyeOSGiActivatorException("Service not found!");
        }
        ((GoodbyeOSGi) bundleContext.getService(serviceRef)).goodbye();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        ServiceReference<?> ref = bundleContext.getServiceReference(GoodbyeOSGi.class.getName());
        if (ref != null) {
            bundleContext.ungetService(ref);
        }
    }
}
