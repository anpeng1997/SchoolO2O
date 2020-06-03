package cn.pengan.dto;

import cn.pengan.entity.LocalAuth;
import cn.pengan.enums.LocalAuthStatusEnum;

public class LocalAuthExecution {
    private int stateCode;
    private String stateInfo;
    private LocalAuth localAuth;

    public LocalAuthExecution() {

    }

    public LocalAuthExecution(LocalAuthStatusEnum statusEnum) {
        this.stateCode = statusEnum.getState();
        this.stateInfo = statusEnum.getStateInfo();
    }

    public LocalAuthExecution(LocalAuthStatusEnum statusEnum, LocalAuth localAuth) {
        this.stateCode = statusEnum.getState();
        this.stateInfo = statusEnum.getStateInfo();
        this.localAuth = localAuth;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public LocalAuth getLocalAuth() {
        return localAuth;
    }

    public void setLocalAuth(LocalAuth localAuth) {
        this.localAuth = localAuth;
    }
}
