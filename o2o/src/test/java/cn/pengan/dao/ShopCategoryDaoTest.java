package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.dto.ShopCategoryPickerModel;
import cn.pengan.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;

public class ShopCategoryDaoTest extends BaseTest {

    @Autowired
    private IShopCategoryDao shopCategoryDao;

    @Test
    public void findCategoryTest() {
        ShopCategory shopCategory1 = new ShopCategory();
        shopCategory1.setShopCategoryName("奶");
        List<ShopCategory> shopCategoryList = shopCategoryDao.findShopCategoryList(shopCategory1);
        for (ShopCategory shopCategory : shopCategoryList) {
            System.out.println(shopCategory);
        }
    }

    @Test
    public void insertCategoryTest() {
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryName("二手市场");
        shopCategory.setShopCategoryDesc("二手交易");
        shopCategory.setCreateTime(new Date());
        shopCategory.setLastEditTime(new Date());
        shopCategory.setPriority(1);
        shopCategory.setShopCategoryImg("/test/testpath");
        shopCategoryDao.insertShopCategory(shopCategory);
        System.out.println(shopCategory);
    }

    @Test
    public void findShopCategoryPickerTest() {
        List<ShopCategoryPickerModel> shopCategoryPickerList = shopCategoryDao.findShopCategoryPickerList();
        shopCategoryPickerList.forEach(System.out::println);
    }
}
