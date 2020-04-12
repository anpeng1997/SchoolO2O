package cn.pengan.service.impl;

import cn.pengan.dao.IShopCategoryDao;
import cn.pengan.entity.ShopCategory;
import cn.pengan.service.IShopCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryImpl implements IShopCategoryService {

    private final IShopCategoryDao shopCategoryDao;

    public ShopCategoryImpl(IShopCategoryDao shopCategoryDao) {
        this.shopCategoryDao = shopCategoryDao;
    }

    @Override
    public List<ShopCategory> findShopCategoryList(ShopCategory shopCategory) {
        return shopCategoryDao.findShopCategoryList(shopCategory);
    }
}
