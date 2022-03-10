package ru.ifmo.pga.hello.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import ru.ifmo.pga.hello.service.impl.HelloOSGiWorld;

public class HelloActivator implements BundleActivator {
    public ServiceRegistration<?> registration;

    @Override
    public void start(BundleContext bundleContext) {
        registration = bundleContext.registerService(HelloOSGi.class.getName(),
                new HelloOSGiWorld(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) {
        registration.unregister();
    }
}
