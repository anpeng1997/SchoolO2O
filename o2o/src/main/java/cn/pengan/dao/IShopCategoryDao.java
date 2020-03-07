package cn.pengan.dao;

import cn.pengan.entity.ShopCategory;

import java.util.List;

public interface IShopCategoryDao {
    public List<ShopCategory> findShopCategoryList(ShopCategory category);
}
