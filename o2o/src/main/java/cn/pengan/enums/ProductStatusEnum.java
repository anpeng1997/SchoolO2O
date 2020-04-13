package cn.pengan.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ProductStatusEnum {
    SUCCESS(1, "Success"),
    INNER_ERROR(-1001, "Fail,server error"),
    IMAGE_EMPTY(-1002, "Fail,img not is empty");

    private int state;
    private String stateInfo;

    ProductStatusEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductStatusEnum statusOf(int index) {
        Optional<ProductStatusEnum> optional = Arrays.stream(values()).filter(p -> p.state == index).findAny();
        return optional.isPresent() ? optional.get() : null;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}
