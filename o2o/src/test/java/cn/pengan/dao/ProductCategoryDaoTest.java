package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private IProductCategoryDao productCategoryDao;


    @Test
    public void findProductCategoryList(){
        List<ProductCategory> productList = productCategoryDao.findProductCategoryList(31L);
        Assert.assertEquals(1,productList.size());
    }
}
