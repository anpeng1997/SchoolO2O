package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryDaoTest extends BaseTest {

    @Autowired
    private IShopCategoryDao shopCategoryDao;

    @Test
    public void findCategoryTest() {
        List<ShopCategory> shopCategoryList = shopCategoryDao.findShopCategoryList(new ShopCategory());
        for (ShopCategory shopCategory : shopCategoryList) {
            System.out.println(shopCategory);
        }
    }
}
