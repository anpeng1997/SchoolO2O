package cn.pengan.exceptions;

public class ShopCategoryOperationException extends RuntimeException {
    public ShopCategoryOperationException() {
        super();
    }

    public ShopCategoryOperationException(String message) {
        super(message);
    }
}
