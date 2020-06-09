package cn.pengan.exceptions;

public class ProductCategoryOperationException extends RuntimeException {

    private static final long serialVersionUID = -6589402084417479676L;

    public ProductCategoryOperationException(String message) {
        super(message);
    }
}
