package cn.pengan.dao;

import cn.pengan.entity.Shop;

public interface IShopDao {
    Shop findShopById(Long shopId);
    int insertShop(Shop shop);
    int updateShop(Shop shop);
    int deleteShop(String shopId);
}
