package pga.goodbye.service.impl;


import pga.goodbye.service.GoodbyeOSGi;

import org.osgi.service.component.annotations.Component;

@Component(service = GoodbyeOSGi.class)
public class GoodbyeOSGiWorld implements GoodbyeOSGi {

    @Override
    public void goodbye() {
        System.out.println("Goodbye OSGi World!");
    }
}
