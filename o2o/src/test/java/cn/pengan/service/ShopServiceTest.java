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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private IShopService shopService;

    @Test
    public void modifyShpTest() throws Exception {
        Shop shop = shopService.findShopById(31L);
        shop.setShopName("修改之后的名称");
        File file = new File("E:\\Users\\an\\Pictures\\Desktop\\MD_Wallpaper_Tree.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        shopService.modifyShop(shop, fileInputStream, file.getName());
    }

    @Test
    public void addShopTest() throws Exception {
        File file = new File("E:\\Users\\an\\Pictures\\Desktop\\pulppixel22.png");
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
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ShopExecution shopExecution = shopService.addShop(shop, fileInputStream, file.getName());
        System.out.println(shopExecution);
    }

    @Test
    public void shopListTest() {
        Shop shopCondition = new Shop();
        shopCondition.setShopName("书");
        ShopExecution shopList = shopService.findShopList(shopCondition, Arrays.asList(9L), 2, 5);
        System.out.println(shopList);
    }
}
