package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.entity.Product;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {
    @Autowired
    private IProductDao productDao;

    @Test
    public void a_InsertProductTest() {
        Product product = new Product();
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        product.setLastEditTime(new Date());
        product.setNormalPrice("20.8");
        product.setPoint(1);
        product.setProductCategoryId(1L);
        product.setPriority(1);
        product.setProductName("妙脆角3");
        product.setProductDesc("油炸食品");
        product.setPromotionPrice("18.8");
        product.setShopId(45L);
        productDao.insertProduct(product);
        Assert.assertTrue(product.getProductId() > 0);
    }

    @Test
    public void b_UpdateProductTest() {
        Product product = new Product();
        product.setProductId(2L);
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        product.setLastEditTime(new Date());
        product.setNormalPrice("18");
        product.setPoint(1);
        product.setProductCategoryId(1L);
        product.setPriority(1);
        product.setProductName("妙脆角3Update");
        product.setProductDesc("油炸食品");
        product.setPromotionPrice("15");
        product.setShopId(45L);
        int i = productDao.updateProduct(product);
        Assert.assertTrue(i > 0);
    }

    @Test
    public void c_findProductTest() {
        Product product = productDao.findProductById(9L);
        System.out.println(product);
        product.getProductImgs().stream().forEach(System.out::println);
    }

    @Test
    public void d_findProductListTest() {
        Product product = new Product();
        product.setShopId(45L);
        List<Product> productList = productDao.findProductList(product,null, 2, 2);
        productList.stream().forEach(System.out::println);
    }

    @Test
    public void e_findProductCount() {
        Product product = new Product();
        product.setShopId(45L);
        int productCount = productDao.findProductCount(product,null);
        System.out.println(productCount);
    }
}
