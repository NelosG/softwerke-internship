package pga.hello.service;


public class HelloOSGiWorld implements HelloOSGi {

    @Override
    public void hello() {
        System.out.println("Hello OSGi World!");
    }
}
