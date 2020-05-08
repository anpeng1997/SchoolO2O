package cn.pengan.service;

import cn.pengan.BaseTest;
import cn.pengan.dto.ShopCategoryExecution;
import cn.pengan.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ShopCategoryServiceTest extends BaseTest {

    @Autowired
    private IShopCategoryService shopCategoryService;

    @Test
    public void findShopCategoryListTest() {
        ShopCategory shopCategory = new ShopCategory();
        //shopCategory.setParentId(1L);
        ShopCategoryExecution execution = shopCategoryService.findShopCategoryList(shopCategory);
        System.out.println(execution);
    }

    @Test
    public void insertShopCategoryTest() throws FileNotFoundException {
        //TODO: 测试完成service,还待完成 shopCategory API controller
        File file = new File("C:\\Users\\pengan\\Pictures\\Saved Pictures\\17-1Z613113243C8.png");
        String name = file.getName();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryName("test category");
        shopCategory.setShopCategoryDesc("test desc");
        shopCategory.setPriority(1);
        FileInputStream fileInputStream = new FileInputStream(file);
        ShopCategoryExecution shopCategoryExecution = shopCategoryService.insertShopCategory(shopCategory, fileInputStream, name);
        System.out.println(shopCategoryExecution);
    }

}
