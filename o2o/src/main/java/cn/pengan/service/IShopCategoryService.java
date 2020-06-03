package cn.pengan.service;

import cn.pengan.dto.ShopCategoryExecution;
import cn.pengan.dto.ShopCategoryPickerModel;
import cn.pengan.entity.ShopCategory;

import java.io.InputStream;
import java.util.List;

public interface IShopCategoryService {
    ShopCategory findShopCategoryById(Long shopCategoryId);

    ShopCategoryExecution updateShopCategory(ShopCategory shopCategory, InputStream inputStream, String fileName);

    ShopCategoryExecution findShopCategoryList(ShopCategory shopCategory);

    List<ShopCategoryPickerModel> findShopCategoryPickerList();

    ShopCategoryExecution insertShopCategory(ShopCategory shopCategory, InputStream categoryImgInput, String fileName);
}
