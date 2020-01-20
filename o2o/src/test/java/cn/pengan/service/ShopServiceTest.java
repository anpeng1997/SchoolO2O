package cn.pengan.service;

import cn.pengan.BaseTest;
import cn.pengan.dto.ShopExecution;
import cn.pengan.entity.Area;
import cn.pengan.entity.PersonInfo;
import cn.pengan.entity.Shop;
import cn.pengan.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private IShopService shopService;


    @Test
    public void addShopTest(){
        File file = new File("E:\\Users\\an\\Downloads\\tiger.jpg");
        Shop shop = new Shop();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        PersonInfo personInfo = new PersonInfo();
        area.setAreaId(3L);
        personInfo.setUserId(1L);
        shopCategory.setShopCategoryId(1L);
        shop.setArea(area);
        shop.setOwner(personInfo);
        shop.setShopCategory(shopCategory);
        shop.setShopName("tiger-tiger_3");
        shop.setEnableStatus(1);
        shop.setPhone("123456789");
        shop.setPriority(1);
        shop.setShopDesc("test");
        ShopExecution shopExecution = shopService.addShop(shop, file);
        System.out.println(shopExecution);
    }
}
