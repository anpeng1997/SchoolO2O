package cn.pengan.service;

import cn.pengan.dto.ProductExecution;
import cn.pengan.entity.Product;
import cn.pengan.entity.Shop;

import java.io.InputStream;
import java.util.Map;

public interface IProductService {
    ProductExecution insertProduct(Product product, Map<String, InputStream> productImgs);

    ProductExecution updateProduct(Product product, Map<String, InputStream> productImgs);

    ProductExecution findProductById(Long productId);

    ProductExecution findProductList(Product productCondition, Long[] productCategoryIds, int pageIndex, int pageSize);

    ProductExecution changeProductStatus(Long productId);

    ProductExecution deleteProductById(Long productId);
}
