package cn.pengan.entity;

import javax.xml.crypto.Data;
import java.util.Date;

public class Shop {
    private Long shopId;
    private Long ownerId;
    private Long areaId;
    private Long shopCategoryId;
    private Long parentCategoryId;
    private String shopName;
    private String shopDesc;
    private String shopAddr;
    private String phone;
    private String shopImg;
    private Double longitude;
    private Double latitude;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private Integer enableStatus;
    private String advice;
}
