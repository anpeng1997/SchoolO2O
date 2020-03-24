package cn.pengan.service;

import cn.pengan.entity.ProductCategory;
import cn.pengan.enums.ProductCategoryExecutionEnum;

import java.util.List;

public interface IProductCategoryService {
    List<ProductCategory> findProductCategoryList(Long shopId);

    ProductCategory findProductCategory(Long categoryId);

    ProductCategoryExecutionEnum deleteProductCategoryById(Long categoryId);

    ProductCategoryExecutionEnum insertProductCategory(ProductCategory productCategory);

    ProductCategoryExecutionEnum updateProductCategory(ProductCategory productCategory);

}
