package cn.pengan.service.impl;

import cn.pengan.dao.IProductCategoryDao;
import cn.pengan.dto.ProductCategoryExecution;
import cn.pengan.entity.ProductCategory;
import cn.pengan.enums.ProductCategoryStatusEnum;
import cn.pengan.exceptions.ProductCategoryOperationException;
import cn.pengan.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {

    private final IProductCategoryDao productCategoryDao;

    public ProductCategoryServiceImpl(IProductCategoryDao productCategoryDao) {
        this.productCategoryDao = productCategoryDao;
    }

    @Override
    public ProductCategoryExecution findProductCategoryList(Long shopId) {
        List<ProductCategory> productCategoryList = productCategoryDao.findProductCategoryList(shopId);
        return new ProductCategoryExecution(ProductCategoryStatusEnum.SUCCESS, productCategoryList);
    }

    @Override
    public ProductCategoryExecution findProductCategory(Long categoryId) {
        ProductCategory productCategory = productCategoryDao.findProductCategory(categoryId);
        return new ProductCategoryExecution(ProductCategoryStatusEnum.SUCCESS, productCategory);
    }

    @Override
    public ProductCategoryExecution deleteProductCategoryById(Long categoryId) throws ProductCategoryOperationException {
        //TODO :商品类别下的商品的商品类别ID还要设置为空
        try {
            int i = productCategoryDao.deleteProductCategoryById(categoryId);
            if (i <= 0) {
                throw new ProductCategoryOperationException("商品类别删除失败！");
            }
            return new ProductCategoryExecution(ProductCategoryStatusEnum.SUCCESS);
        } catch (Exception ex) {
            throw new ProductCategoryOperationException("deleteProductCategoryById error:" + ex.getMessage());
        }
    }

    @Override
    public ProductCategoryExecution insertProductCategory(ProductCategory productCategory) throws ProductCategoryOperationException {
        try {
            int i = productCategoryDao.insertProductCategory(productCategory);
            if (i <= 0) {
                throw new ProductCategoryOperationException("商品类别添加失败！");
            }
            return new ProductCategoryExecution(ProductCategoryStatusEnum.SUCCESS);
        } catch (Exception ex) {
            throw new ProductCategoryOperationException("insertProductCategory error:" + ex.getMessage());
        }
    }

    @Override
    public ProductCategoryExecution updateProductCategory(ProductCategory productCategory) throws ProductCategoryOperationException {
        productCategory.setLastEditTime(new Date());
        try {
            int i = productCategoryDao.updateProductCategory(productCategory);
            if (i <= 0) {
                throw new ProductCategoryOperationException("商品类别更新失败！");
            }
            return new ProductCategoryExecution(ProductCategoryStatusEnum.SUCCESS);
        } catch (Exception ex) {
            throw new ProductCategoryOperationException("updateProductCategory error:" + ex.getMessage());
        }
    }
}
