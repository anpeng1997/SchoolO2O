package cn.pengan.dao;

import cn.pengan.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IShopDao {
    Shop findShopById(Long shopId);

    /**
     * 根据条件获取商店列表
     *
     * @param shopCondition   商店条件
     * @param shopCategoryIds 商店子类别条件（根据多类别筛选）
     * @param offset
     * @param pageSize
     * @return
     */
    List<Shop> findShopList(@Param("shopCondition") Shop shopCondition, @Param("categoryIds") List<Long> shopCategoryIds, @Param("offset") int offset, @Param("pageSize") int pageSize);

    int insertShop(Shop shop);

    int updateShop(Shop shop);

    int findShopCount(@Param("shopCondition") Shop shopCondition, @Param("categoryIds") List<Long> shopCategoryIds);

    int deleteShop(String shopId);

}
