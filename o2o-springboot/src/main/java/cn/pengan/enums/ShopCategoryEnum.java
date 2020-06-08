package cn.pengan.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ShopCategoryEnum {
    SUCCESS(1, "操作成功"),
    FAIL(-1, "操作失败"),
    DATE_EMPTY(-1000, "数据为空"),
    INNER_ERROR(-1001, "内部错误"),
    DATE_ERROR(-1002, "数据错误");

    private int state;
    private String stateInfo;


    ShopCategoryEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopCategoryEnum statusOf(int state) {
        Optional<ShopCategoryEnum> optional = Arrays.stream(values()).filter(p -> p.state == state).findAny();
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
