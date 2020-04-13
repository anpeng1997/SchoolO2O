package cn.pengan.service;

import cn.pengan.BaseTest;
import cn.pengan.dto.ProductExecution;
import cn.pengan.entity.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class ProductServiceTest extends BaseTest {

    @Autowired
    private IProductService productService;

    @Test
    public void insertProductTest() throws FileNotFoundException {
        Product product = new Product();
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        product.setLastEditTime(new Date());
        product.setNormalPrice("20.8");
        product.setPoint(1);
        product.setProductCategoryId(1L);
        product.setPriority(1);
        product.setProductName("妙脆角123456");
        product.setProductDesc("油炸食品");
        product.setPromotionPrice("18.8");
        product.setShopId(45L);
        File file = new File("E:\\Users\\an\\Pictures\\Desktop\\MD_Wallpaper_Tree.png");
        InputStream inputStream = new FileInputStream(file);
        Map<String, InputStream> files = new LinkedHashMap<>();
        files.put("test1.png", inputStream);
        files.put("test2.png", inputStream);
        ProductExecution productExecution = productService.insertProduct(product, files);
        System.out.println(productExecution);
    }

    @Test
    public void changeStatusTest(){
        ProductExecution execution = productService.changeProductStatus(13L);
        System.out.println(execution);
    }

}
