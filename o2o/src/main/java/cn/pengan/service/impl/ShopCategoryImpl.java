package cn.pengan.service.impl;

import cn.pengan.dao.IShopCategoryDao;
import cn.pengan.dto.ShopCategoryExecution;
import cn.pengan.entity.ShopCategory;
import cn.pengan.enums.ShopCategoryEnum;
import cn.pengan.service.IShopCategoryService;
import cn.pengan.util.FileUtil;
import cn.pengan.util.ImageUtil;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ShopCategoryImpl implements IShopCategoryService {

    private final IShopCategoryDao shopCategoryDao;

    public ShopCategoryImpl(IShopCategoryDao shopCategoryDao) {
        this.shopCategoryDao = shopCategoryDao;
    }

    @Override
    public ShopCategoryExecution findShopCategoryList(ShopCategory shopCategory) {
        List<ShopCategory> shopCategoryList = shopCategoryDao.findShopCategoryList(shopCategory);
        int count = shopCategoryDao.findCount(shopCategory);
        ShopCategoryExecution shopCategoryExecution = new ShopCategoryExecution(ShopCategoryEnum.SUCCESS, shopCategoryList, count);
        return shopCategoryExecution;
    }

    @Override
    public ShopCategoryExecution insertShopCategory(ShopCategory shopCategory, InputStream categoryImgInput,String fileName) {
        //TODO: 保存图片后插入图片
        shopCategoryDao.insertShopCategory(shopCategory);
        return new ShopCategoryExecution();
    }
}
