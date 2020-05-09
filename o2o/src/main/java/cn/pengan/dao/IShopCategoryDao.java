package cn.pengan.dao;

import cn.pengan.entity.ShopCategory;

import java.util.List;

public interface IShopCategoryDao {
    List<ShopCategory> findShopCategoryList(ShopCategory category);

    int findCount(ShopCategory condition);

    int insertShopCategory(ShopCategory shopCategory);

    int updateShopCategory(ShopCategory shopCategory);
}
