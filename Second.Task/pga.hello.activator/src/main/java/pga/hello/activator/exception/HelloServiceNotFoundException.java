package pga.hello.activator.exception;

public class HelloServiceNotFoundException extends Exception{
    public HelloServiceNotFoundException() {
        super();
    }

    public HelloServiceNotFoundException(String message) {
        super(message);
    }

    public HelloServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HelloServiceNotFoundException(Throwable cause) {
        super(cause);
    }
}
