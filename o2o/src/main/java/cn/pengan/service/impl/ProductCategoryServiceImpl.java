package cn.pengan.service.impl;

import cn.pengan.dao.IProductCategoryDao;
import cn.pengan.entity.ProductCategory;
import cn.pengan.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {

    @Autowired
    private IProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> findProductCategoryList(Long shopId) {
        return productCategoryDao.findProductCategoryList(shopId);
    }

    @Override
    public int deleteProductCategoryById(Long categoryId) {
        return  productCategoryDao.deleteProductCategoryById(categoryId);
    }

    @Override
    public int insertProductCategory(ProductCategory productCategory) {
        return  productCategoryDao.insertProductCategory(productCategory);
    }
}
