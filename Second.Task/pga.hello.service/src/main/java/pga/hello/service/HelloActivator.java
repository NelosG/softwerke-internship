package pga.hello.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pga.hello.service.impl.HelloOSGiWorld;

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
