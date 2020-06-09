package cn.pengan.dao;

import cn.pengan.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductCategoryDao {
    List<ProductCategory> findProductCategoryList(Long shopId);

    ProductCategory findProductCategory(Long categoryId);

    int deleteProductCategoryById(Long categoryId);

    int insertProductCategory(ProductCategory productCategory);

    int updateProductCategory(ProductCategory productCategory);
}
