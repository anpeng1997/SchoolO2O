package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.entity.Area;
import cn.pengan.entity.PersonInfo;
import cn.pengan.entity.Shop;
import cn.pengan.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private IShopDao shopDao;

    @Test
    public void insertShopTest() {
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
        shop.setShopName("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setPhone("123456789");
        shop.setPriority(1);
        shop.setAdvice("审核中");
        shop.setShopDesc("test");
        shop.setShopImg("test url");
        int i = shopDao.insertShop(shop);
        Assert.assertEquals(1, i);
    }

    @Test
    public void findShopByIdTest() {
        Shop shop = shopDao.findShopById(31L);
        System.out.println(shop.getArea().getAreaName());
        System.out.println(shop);
    }

    @Test
    public void updateShopTest() {
        Shop shop = shopDao.findShopById(31L);
        shop.setShopName("张阿姨奶茶店");
        shopDao.updateShop(shop);
    }

    @Test
    public void findShopListTest() {
        Shop shopCondition = new Shop();
//        shopCondition.setShopName("书");
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);

        List<Shop> shopList = shopDao.findShopList(shopCondition, Arrays.asList(9L), 0, 5);
        //Assert.assertEquals(4, shopList.size());
        for (Shop shop : shopList) {
            System.out.println(shop);
        }
    }

    @Test
    public void findShopCountTest() {
        Shop shopCondition = new Shop();
        shopCondition.setShopName("书");
//        PersonInfo owner = new PersonInfo();
//        owner.setUserId(1L);
//        shopCondition.setOwner(owner);
        int shopCount = shopDao.findShopCount(shopCondition, null);
        System.out.println(shopCount);
    }

}
