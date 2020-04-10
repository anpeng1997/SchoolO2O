package cn.pengan.service;

import cn.pengan.dto.ProductExecution;
import cn.pengan.entity.Product;

import java.io.InputStream;
import java.util.Map;

public interface IProductService {
    ProductExecution insertProduct(Product product, Map<String, InputStream> productImgs);

    ProductExecution updateProduct(Product product, Map<String, InputStream> productImgs);

    ProductExecution findProductById(Long productId);

    ProductExecution findProductList(Long shopId, int pageIndex, int pageSize);
}
