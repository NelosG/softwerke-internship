package pga.goodbye.service.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pga.goodbye.service.GoodbyeOSGi;

public class GoodbyeActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) {
        System.out.println("GoodbyeActivator is activated");
        bundleContext.registerService(GoodbyeOSGi.class.getName(),
                new GoodbyeOSGiWorld(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("GoodbyeActivator is deactivated");
    }
}
