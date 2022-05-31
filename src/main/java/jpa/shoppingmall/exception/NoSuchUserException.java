package jpa.shoppingmall.exception;

public class NoSuchUserException extends RuntimeException{
    public NoSuchUserException() {
        super();
    }

    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchUserException(Throwable cause) {
        super(cause);
    }

    public NoSuchUserException(String s) {
        super(s);
    }
}
