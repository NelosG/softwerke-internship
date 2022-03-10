package ru.ifmo.pga.hello.service.impl;


import ru.ifmo.pga.hello.service.HelloOSGi;

public class HelloOSGiWorld implements HelloOSGi {

    @Override
    public void hello() {
        System.out.println("Hello OSGi World!");
    }
}
