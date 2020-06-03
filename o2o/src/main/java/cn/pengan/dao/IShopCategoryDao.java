package cn.pengan.dao;

import cn.pengan.entity.ShopCategory;

import java.util.List;

public interface IShopCategoryDao {
    ShopCategory findShopCategoryById(Long shopCategoryId);

    List<ShopCategory> findShopCategoryList(ShopCategory category);

    int findCount(ShopCategory condition);

    int insertShopCategory(ShopCategory shopCategory);

    int updateShopCategory(ShopCategory shopCategory);
}
