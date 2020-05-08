package cn.pengan.exceptions;

public class ProductOperationException extends RuntimeException {
    private static final long serialVersionUID = -1128506839259184056L;

    public ProductOperationException() {
        super();
    }

    public ProductOperationException(String message) {
        super(message);
    }
}
