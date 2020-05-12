package cn.pengan.service;

import cn.pengan.dto.ShopCategoryExecution;
import cn.pengan.entity.ShopCategory;

import java.io.InputStream;

public interface IShopCategoryService {
    ShopCategory findShopCategoryById(Long shopCategoryId);

    ShopCategoryExecution updateShopCategory(ShopCategory shopCategory, InputStream inputStream, String fileName);

    ShopCategoryExecution findShopCategoryList(ShopCategory shopCategory);

    ShopCategoryExecution insertShopCategory(ShopCategory shopCategory, InputStream categoryImgInput, String fileName);
}
