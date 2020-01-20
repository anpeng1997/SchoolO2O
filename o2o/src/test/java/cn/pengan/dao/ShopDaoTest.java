package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.entity.Area;
import cn.pengan.entity.PersonInfo;
import cn.pengan.entity.Shop;
import cn.pengan.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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
        Assert.assertEquals(true, i > 1);
    }

    @Test
    public void findShopByIdTest() {
        Shop shop = shopDao.findShopById(1L);
        System.out.println(shop);
    }

    @Test
    public void updateShopTest(){
        Shop shop = shopDao.findShopById(1L);
        shop.setShopName("张阿姨奶茶店");
        shopDao.updateShop(shop);
    }
}
