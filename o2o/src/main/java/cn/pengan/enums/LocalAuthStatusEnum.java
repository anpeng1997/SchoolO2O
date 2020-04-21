package cn.pengan.enums;

import java.util.Arrays;
import java.util.Optional;

public enum LocalAuthStatusEnum {
    SUCCESS(1, "操作成功"),
    FAIL(-1, "操作失败"),
    DUPLICATE_KEY(-1003,"添加数据时重复key错误"),
    INNER_ERROR(-1001,"内部错误");

    private int state;
    private String stateInfo;

    LocalAuthStatusEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static LocalAuthStatusEnum statusOf(int state){
        Optional<LocalAuthStatusEnum> optional = Arrays.stream(values()).filter(p -> p.state == state).findAny();
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
