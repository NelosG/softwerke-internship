package ru.ifmo.pga.goodbye.service.impl;


import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import ru.ifmo.pga.goodbye.service.GoodbyeOSGi;

/**
 * Singleton component, that implements {@link GoodbyeOSGi} service.
 */
@Component(
        service = GoodbyeOSGi.class,
        immediate = true
)
@ServiceVendor("Pushkarev Gleb")
@ServiceDescription("Provides a friendly farewell.")
public class GoodbyeOSGiWorld implements GoodbyeOSGi {

    @Override
    public void goodbye() {
        System.out.println("Goodbye OSGi World!");
    }
}
