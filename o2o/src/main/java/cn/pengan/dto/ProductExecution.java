package cn.pengan.dto;

import cn.pengan.entity.Product;
import cn.pengan.entity.Shop;
import cn.pengan.enums.ProductStatusEnum;

import java.util.List;

public class ProductExecution {
    private int state;
    private String stateInfo;
    private int count;
    private Product product;
    private List<Product> productList;

    public ProductExecution(ProductStatusEnum statusEnum) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getStateInfo();
    }

    public ProductExecution(ProductStatusEnum statusEnum,Product product) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getStateInfo();
        this.product = product;
    }

    public ProductExecution(ProductStatusEnum statusEnum, List<Product> productList) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getStateInfo();
        this.productList = productList;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
