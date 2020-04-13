package cn.pengan.exceptions;

public class ShopOperationException extends RuntimeException {

    private static final long serialVersionUID = -5711503212155359552L;

    public ShopOperationException(String message) {
        super(message);
    }
}
