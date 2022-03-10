package ru.ifmo.pga.hello.command.impl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import ru.ifmo.pga.hello.command.HelloCommand;

@Component(
        service = HelloCommand.class,
        property = {
                "osgi.command.scope=practice",
                "osgi.command.function=hello"
        }
)
@ServiceVendor("Pushkarev Gleb")
@ServiceDescription("Provides a friendly greeting.")
public class HelloOSGiCommand implements HelloCommand {

    @Override
    public void hello(String nameOfUser) {
        System.out.println("Hello, " + nameOfUser);
    }

    public void hello() {
        System.out.println("Please specify one username.");
    }

    public void hello(String... args) {
        System.out.println("Please specify one username.");
    }
}
