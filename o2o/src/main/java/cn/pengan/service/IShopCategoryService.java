package cn.pengan.service;

import cn.pengan.dto.ShopCategoryExecution;
import cn.pengan.entity.ShopCategory;

import java.io.InputStream;
import java.util.List;

public interface IShopCategoryService {
    ShopCategoryExecution findShopCategoryList(ShopCategory shopCategory);
    ShopCategoryExecution insertShopCategory(ShopCategory shopCategory, InputStream categoryImgInput,String fileName);
}
