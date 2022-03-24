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
    public void hello(String... nameOfUsers) {
        if(nameOfUsers.length != 1) {
            System.out.println("Please specify one username.");
            return;
        }
        System.out.println("Hello, " + nameOfUsers[0]);
    }
}
