package cn.pengan.entity;

import java.util.Date;

public class Product {
    private Long productId;
    private String productName;
    private String productDesc;
    private String imgAddr;
    private String normalPrice;
    private String promotionPrice;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private Integer enableStatus;
    private Integer point;
    private Long productCategoryId;
    private Long shopId;
}
