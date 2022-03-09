package pga.goodbye.service.impl;


import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import pga.goodbye.service.GoodbyeOSGi;

/**
 * Singleton component that implements {@link pga.goodbye.service.GoodbyeOSGi} service.
 */
@Component(
        service = GoodbyeOSGi.class,
        immediate = true
//        immediate = true,
//        scope= ServiceScope.SINGLETON,
//        configurationPid = "pga.goodbye.service.impl.GoodbyeOSGiWorld",
//        reference = @Reference(name = "GoodbyeOSGiWorld", service = GoodbyeOSGi.class),
)
@ServiceVendor("Pushkarev Gleb")
@ServiceDescription("Provides a friendly farewell.")
public class GoodbyeOSGiWorld implements GoodbyeOSGi {

    @Override
    public void goodbye() {
        System.out.println("Goodbye OSGi World!");
    }
}
