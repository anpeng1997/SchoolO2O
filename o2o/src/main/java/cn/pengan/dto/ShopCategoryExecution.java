package cn.pengan.dto;

import cn.pengan.entity.ShopCategory;
import cn.pengan.enums.ShopCategoryEnum;

import java.util.List;

public class ShopCategoryExecution {
    private int state;
    private String stateInfo;
    private int count;
    private ShopCategory shopCategory;
    private List<ShopCategory> shopCategoryList;

    public ShopCategoryExecution() {
    }
    public ShopCategoryExecution(ShopCategoryEnum shopCategoryEnum) {
        state = shopCategoryEnum.getState();
        stateInfo = shopCategoryEnum.getStateInfo();
    }

    public ShopCategoryExecution(ShopCategoryEnum shopCategoryEnum, ShopCategory shopCategory) {
        state = shopCategoryEnum.getState();
        stateInfo = shopCategoryEnum.getStateInfo();
        this.shopCategory = shopCategory;
    }

    public ShopCategoryExecution(ShopCategoryEnum shopCategoryEnum, List<ShopCategory> shopCategories, int count) {
        state = shopCategoryEnum.getState();
        stateInfo = shopCategoryEnum.getStateInfo();
        this.shopCategoryList = shopCategories;
        this.count = count;
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

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ShopCategory shopCategory) {
        this.shopCategory = shopCategory;
    }

    public List<ShopCategory> getShopCategoryList() {
        return shopCategoryList;
    }

    public void setShopCategoryList(List<ShopCategory> shopCategoryList) {
        this.shopCategoryList = shopCategoryList;
    }

    @Override
    public String toString() {
        return "ShopCategoryExecution{" +
                "state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", count=" + count +
                ", shopCategory=" + shopCategory +
                ", shopCategoryList=" + shopCategoryList +
                '}';
    }
}
