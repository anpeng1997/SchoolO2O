package cn.pengan.service;

import cn.pengan.entity.Product;
import cn.pengan.entity.ProductCategory;

import java.util.List;

public interface IProductCategoryService {
    List<ProductCategory> findProductCategoryList(Long shopId);

    int deleteProductCategoryById(Long categoryId);

    int insertProductCategory(ProductCategory productCategory);
}
