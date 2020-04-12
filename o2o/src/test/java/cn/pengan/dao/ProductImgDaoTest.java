package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.entity.ProductImg;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;

public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private IProductImgDao productImgDao;

    @Test
    public void batchInsertImgTest() {
        ArrayList<ProductImg> productImgs = new ArrayList<>();
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("/test/test");
        productImg1.setCreateTime(new Date());
        productImg1.setImgDesc("testDesc");
        productImg1.setPriority(1);
        productImg1.setProductId(1L);
        productImgs.add(productImg1);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("/test/test");
        productImg2.setCreateTime(new Date());
        productImg2.setImgDesc("testDesc");
        productImg2.setPriority(1);
        productImg2.setProductId(1L);
        productImgs.add(productImg2);
        ProductImg productImg3 = new ProductImg();
        productImg3.setImgAddr("/test/test");
        productImg3.setCreateTime(new Date());
        productImg3.setImgDesc("testDesc");
        productImg3.setPriority(1);
        productImg3.setProductId(1L);
        productImgs.add(productImg3);
        int i = productImgDao.batchInsertProductImg(productImgs);
        Assert.assertEquals(3, i);
    }
}
