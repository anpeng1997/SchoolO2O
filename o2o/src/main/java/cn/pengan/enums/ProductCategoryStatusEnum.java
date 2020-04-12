package cn.pengan.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ProductCategoryStatusEnum {
    SUCCESS(1, "操作成功！"),
    FAIL(-1, "操作失败"),
    INTERNAL_ERR(-1001, "内部错误！"),
    NULL_SHOP_ID(-1002, "shopId为空"),
    EMPTY_LIST(-1002, "商品类别列表为空！");

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
