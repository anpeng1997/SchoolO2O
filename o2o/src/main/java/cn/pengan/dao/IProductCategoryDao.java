package cn.pengan.dao;

import cn.pengan.entity.ProductCategory;

import java.util.List;

public interface IProductCategoryDao {
    List<ProductCategory> findProductCategoryList(Long shopId);

    ProductCategory findProductCategory(Long categoryId);

    int deleteProductCategoryById(Long categoryId);

    int insertProductCategory(ProductCategory productCategory);

    int updateProductCategory(ProductCategory productCategory);
}
