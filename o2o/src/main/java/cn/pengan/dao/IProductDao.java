package cn.pengan.dao;

import cn.pengan.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProductDao {
    int insertProduct(Product product);

    int updateProduct(Product product);

    Product findProductById(Long productId);

    List<Product> findProductList(@Param("shopId") Long shopId, @Param("offset") int offset, @Param("pageSize") int pageSize);

    int findProductCount(Long shopId);

    int deleteProduct(Long productId);
}
