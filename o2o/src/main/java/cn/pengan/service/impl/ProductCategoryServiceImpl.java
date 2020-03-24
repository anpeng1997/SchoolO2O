package cn.pengan.service.impl;

import cn.pengan.dao.IProductCategoryDao;
import cn.pengan.entity.ProductCategory;
import cn.pengan.enums.ProductCategoryExecutionEnum;
import cn.pengan.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public ProductCategory findProductCategory(Long categoryId) {
        return productCategoryDao.findProductCategory(categoryId);
    }

    @Override
    public ProductCategoryExecutionEnum deleteProductCategoryById(Long categoryId) {
        try {
            int i = productCategoryDao.deleteProductCategoryById(categoryId);
            if (i >= 1) {
                return ProductCategoryExecutionEnum.SUCCESS;
            } else {
                return ProductCategoryExecutionEnum.FAIL;
            }
        } catch (Exception ex) {
            return ProductCategoryExecutionEnum.INTERNAL_ERR;
        }
    }

    @Override
    public ProductCategoryExecutionEnum insertProductCategory(ProductCategory productCategory) {
        try {
            int i = productCategoryDao.insertProductCategory(productCategory);
            if (i >= 1) {
                return ProductCategoryExecutionEnum.SUCCESS;
            } else {
                return ProductCategoryExecutionEnum.FAIL;
            }
        } catch (Exception ex) {
            return ProductCategoryExecutionEnum.INTERNAL_ERR;
        }
    }

    @Override
    public ProductCategoryExecutionEnum updateProductCategory(ProductCategory productCategory) {
        productCategory.setLastEditTime(new Date());
        try {
            int i = productCategoryDao.updateProductCategory(productCategory);
            if (i >= 1) {
                return ProductCategoryExecutionEnum.SUCCESS;
            } else {
                return ProductCategoryExecutionEnum.FAIL;
            }
        } catch (Exception ex) {
            return ProductCategoryExecutionEnum.INTERNAL_ERR;
        }
    }
}
