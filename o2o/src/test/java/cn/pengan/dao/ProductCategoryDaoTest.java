package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.entity.ProductCategory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//测试类中的测试方法执行顺序
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private IProductCategoryDao productCategoryDao;

    @Test
    public void findProductCategoryList() {
        List<ProductCategory> productList = productCategoryDao.findProductCategoryList(31L);
        Assert.assertEquals(1, productList.size());
    }

    @Test
    public void insertProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("test1");
        productCategory.setProductCategoryDesc("test2");
        productCategory.setLastEditTime(new Date());
        productCategory.setShopId(45L);
        productCategory.setCreateTime(new Date());
        productCategoryDao.insertProductCategory(productCategory);
        System.out.println(productCategory.getProductCategoryId());
    }

}
