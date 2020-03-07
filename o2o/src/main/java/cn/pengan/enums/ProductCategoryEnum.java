package cn.pengan.enums;

import cn.pengan.entity.ProductCategory;

public enum ProductCategoryEnum {
    INTERNAL_ERR(-1001,"内部错误！"),
    EMPTY_LIST(-1002,"商品类别列表为空！");

    private  int status;
    private String statusInfo;

    ProductCategoryEnum(int status,String statusInfo) {
        this.status=status;
        this.statusInfo=statusInfo;
    }

    public static ProductCategoryEnum statusOf(int index) {
        for (ProductCategoryEnum value : values()) {
            if (value.status == index){
                return value;
            }
        }
        return null;
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
