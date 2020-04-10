package cn.pengan.exceptions;

public class ProductException extends RuntimeException {
    private static final long serialVersionUID = -1128506839259184056L;

    public ProductException() {
    }

    public ProductException(String message) {
        super(message);
    }
}
