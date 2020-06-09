package cn.pengan.service;

import cn.pengan.dto.ProductCategoryExecution;
import cn.pengan.entity.ProductCategory;
import cn.pengan.enums.ProductCategoryStatusEnum;

import java.util.List;

public interface IProductCategoryService {
    ProductCategoryExecution findProductCategoryList(Long shopId);

    ProductCategoryExecution findProductCategory(Long categoryId);

    ProductCategoryExecution deleteProductCategoryById(Long categoryId);

    ProductCategoryExecution insertProductCategory(ProductCategory productCategory);

    ProductCategoryExecution updateProductCategory(ProductCategory productCategory);

}
