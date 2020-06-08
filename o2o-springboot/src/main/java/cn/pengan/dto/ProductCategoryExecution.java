package cn.pengan.dto;

import cn.pengan.entity.ProductCategory;
import cn.pengan.enums.ProductCategoryStatusEnum;

import java.util.List;

public class ProductCategoryExecution {
    private int state;
    private String stateInfo;
    private ProductCategory productCategory;
    private List<ProductCategory> productCategories;

    public ProductCategoryExecution() {
    }

    public ProductCategoryExecution(ProductCategoryStatusEnum statusEnum) {
        this.state = statusEnum.getStatus();
        this.stateInfo = statusEnum.getStatusInfo();
    }

    public ProductCategoryExecution(ProductCategoryStatusEnum statusEnum, ProductCategory productCategory) {
        this.state = statusEnum.getStatus();
        this.productCategory = productCategory;
    }

    public ProductCategoryExecution(ProductCategoryStatusEnum statusEnum, List<ProductCategory> productCategories) {
        this.state = statusEnum.getStatus();
        this.productCategories = productCategories;
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

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }
}
