package cn.pengan.service;

import cn.pengan.BaseTest;
import cn.pengan.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryTest extends BaseTest {

    @Autowired
    private IShopCategoryService shopCategoryService;

    @Test
    public void findShopCategoryListTest() {
        ShopCategory shopCategory = new ShopCategory();
        //shopCategory.setParentId(1L);
        List<ShopCategory> shopCategoryList = shopCategoryService.findShopCategoryList(shopCategory);
        Assert.assertEquals(2, shopCategoryList.size());
    }
}
