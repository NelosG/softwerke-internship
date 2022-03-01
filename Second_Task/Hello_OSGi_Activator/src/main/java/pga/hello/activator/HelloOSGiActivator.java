package pga.hello.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import pga.hello.service.HelloOSGi;

public class HelloOSGiActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) {
        ServiceReference<?> ref = bundleContext.getServiceReference(HelloOSGi.class.getName());
        ((HelloOSGi) bundleContext.getService(ref)).hello();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        ServiceReference<?> ref = bundleContext.getServiceReference(HelloOSGi.class.getName());
        if (ref != null){
            bundleContext.ungetService(ref);
        }
        System.out.println("HelloOSGiActivator has stopped");
    }
}
