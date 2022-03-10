package ru.ifmo.pga.hello.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import ru.ifmo.pga.hello.activator.exception.HelloOSGiActivatorException;
import ru.ifmo.pga.hello.service.HelloOSGi;

public class HelloOSGiActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws HelloOSGiActivatorException {
        ServiceReference<?> serviceRef = bundleContext.getServiceReference(HelloOSGi.class.getName());
        if (serviceRef == null) {
            throw new HelloOSGiActivatorException("Service not found!");
        }
        ((HelloOSGi) bundleContext.getService(serviceRef)).hello();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        ServiceReference<?> serviceRef = bundleContext.getServiceReference(HelloOSGi.class.getName());
        if (serviceRef != null) {
            bundleContext.ungetService(serviceRef);
        }
    }
}
