package cn.pengan.service;

import cn.pengan.entity.ShopCategory;

import java.util.List;

public interface IShopCategoryService {
    public List<ShopCategory> findShopCategoryList(ShopCategory shopCategory);
}
