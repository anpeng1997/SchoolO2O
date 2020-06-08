package cn.pengan.dao;

import cn.pengan.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IProductCategoryDao {
    List<ProductCategory> findProductCategoryList(Long shopId);

    ProductCategory findProductCategory(Long categoryId);

    int deleteProductCategoryById(Long categoryId);

    int insertProductCategory(ProductCategory productCategory);

    int updateProductCategory(ProductCategory productCategory);
}
