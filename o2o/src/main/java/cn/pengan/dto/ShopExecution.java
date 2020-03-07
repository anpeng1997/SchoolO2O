package cn.pengan.dto;

import cn.pengan.entity.Shop;
import cn.pengan.enums.ShopStatusEnum;

import java.util.List;

public class ShopExecution {
    private int state;
    private String stateInfo;
    private int count;
    private Shop shop;
    private List<Shop> shopList;

    public ShopExecution(){}

    //失败调用的构造器
    public ShopExecution(ShopStatusEnum statusEnum) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getStateInfo();
    }

    //成功调用的构造器
    public ShopExecution(ShopStatusEnum statusEnum, Shop shop) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getStateInfo();
        this.shop = shop;
    }

    //成功调用的构造器
    public ShopExecution(ShopStatusEnum statusEnum, List<Shop> shopList) {
        this.state = statusEnum.getState();
        this.stateInfo = statusEnum.getStateInfo();
        this.shopList = shopList;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    @Override
    public String toString() {
        return "ShopExecution{" +
                "state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", count=" + count +
                '}';
    }
}
