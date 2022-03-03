package pga.hello.service.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pga.hello.service.HelloOSGi;

public class HelloActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) {
        bundleContext.registerService(HelloOSGi.class.getName(),
                new HelloOSGiWorld(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) {

    }
}
