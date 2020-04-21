package cn.pengan.exceptions;

public class LocalAuthException extends RuntimeException {

    private static final long serialVersionUID = -2952179971353141034L;

    public LocalAuthException() {
    }

    public LocalAuthException(String message) {
        super(message);
    }
}
