package cn.pengan.dao;

import cn.pengan.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProductDao {
    int insertProduct(Product product);

    int updateProduct(Product product);

    Product findProductById(Long productId);

    List<Product> findProductList(@Param("condition") Product productCondition,
                                  @Param("categoryIds") Long[] categoryIds,
                                  @Param("offset") int offset,
                                  @Param("pageSize") int pageSize);

    int findProductCount(@Param("condition") Product productCondition,
                         @Param("categoryIds") Long[] ids);

    int deleteProduct(Long productId);

    int updateProductCategoryToNull(Long productCategoryId);
}
