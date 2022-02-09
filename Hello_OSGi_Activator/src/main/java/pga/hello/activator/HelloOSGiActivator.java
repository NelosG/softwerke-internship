package pga.hello.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pga.hello.service.HelloOSGiWorld;

public class HelloOSGiActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) {
        new HelloOSGiWorld().hello();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("HelloActivator has stopped");
    }
}
