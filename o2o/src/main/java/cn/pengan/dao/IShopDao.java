package cn.pengan.dao;

import cn.pengan.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IShopDao {
    Shop findShopById(Long shopId);
    List<Shop> findShopList(@Param("shopCondition") Shop shopCondition, @Param("offset") int offset, @Param("pageSize") int pageSize);
    int insertShop(Shop shop);
    int updateShop(Shop shop);
    int findShopCount(@Param("shopCondition") Shop shopCondition);
    int deleteShop(String shopId);
}
