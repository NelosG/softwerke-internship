package pga.goodbye.activator;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import pga.goodbye.service.GoodbyeOSGi;

public class GoodbyeOSGiActivator implements BundleActivator {

    @Reference(target = "pga.goodbye.service.GoodbyeOSGi")
    protected GoodbyeOSGi goodbyeOSGiWorld;


    @Activate
    @Override
    public void start(BundleContext bundleContext) {
        goodbyeOSGiWorld.goodbye();
    }

    @Deactivate
    @Override
    public void stop(BundleContext bundleContext) {
        ServiceReference<?> ref = bundleContext.getServiceReference(GoodbyeOSGi.class.getName());
        if (ref != null){
            bundleContext.ungetService(ref);
        }
        System.out.println("GoodbyeOSGiActivator has stopped");
    }
}
