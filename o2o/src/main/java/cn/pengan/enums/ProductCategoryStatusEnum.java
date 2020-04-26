package cn.pengan.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ProductCategoryStatusEnum {
    SUCCESS(1, "Success"),
    FAIL(-1, "fail"),
    INFO_MISSING(-1000, "fail,missing information"),
    INTERNAL_ERR(-1001, "fail,internal error"),
    NULL_SHOP_ID(-1002, "fail,shopId not is empty"),
    EMPTY_LIST(-1002, "fail,product category list not is empty");

    private int status;
    private String statusInfo;

    ProductCategoryStatusEnum(int status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public static ProductCategoryStatusEnum statusOf(int index) {
        Optional<ProductCategoryStatusEnum> optional = Arrays.stream(values()).filter(p -> p.status == index).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }
}
