package cn.pengan.dao;

import cn.pengan.entity.ProductCategory;

import java.util.List;

public interface IProductCategoryDao {
    List<ProductCategory> findProductCategoryList(Long shopId);
}
