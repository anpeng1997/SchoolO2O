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
}
