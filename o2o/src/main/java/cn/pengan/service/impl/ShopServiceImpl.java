package cn.pengan.service.impl;

import cn.pengan.annotations.DataOperationLog;
import cn.pengan.dao.IShopDao;
import cn.pengan.dto.ShopExecution;
import cn.pengan.entity.Shop;
import cn.pengan.enums.ShopStatusEnum;
import cn.pengan.exceptions.ShopOperationException;
import cn.pengan.service.IShopService;
import cn.pengan.util.CalculatorPaging;
import cn.pengan.util.FileUtil;
import cn.pengan.util.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ShopServiceImpl implements IShopService {

    private final IShopDao shopDao;

    public ShopServiceImpl(IShopDao shopDao) {
        this.shopDao = shopDao;
    }


    @Override
    @Transactional
    @DataOperationLog("修改商店信息")
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStatusEnum.NULL_SHOP_INFO);
        }
        try {
            //判断是否上传的新的图片
            if (shopImgInputStream != null && fileName != null && !"".equals(fileName)) {
                //先查出旧的shop
                Shop tempShop = shopDao.findShopById(shop.getShopId());
                if (tempShop == null) {
                    return new ShopExecution(ShopStatusEnum.NULL_SHOP_INFO);
                }
                if (tempShop.getShopImg() != null) {
                    //删除原来的图片
                    FileUtil.deleteFileOrDirectory(tempShop.getShopImg());
                }
                //保存新的图片
                String newFilPath = ImageUtil.saveShopImg(shop.getShopId(), shopImgInputStream, fileName);
                shop.setShopImg(newFilPath);
            }
            shop.setLastEditTime(new Date());
            int affectedNum = shopDao.updateShop(shop);
            if (affectedNum <= 0) {
                return new ShopExecution(ShopStatusEnum.INNER_ERROR);
            }
            Shop newShop = shopDao.findShopById(shop.getShopId());
            return new ShopExecution(ShopStatusEnum.SUCCESS, newShop);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ShopOperationException("modify shop exception!");
        }
    }

    @Override
    @Transactional
    @DataOperationLog("添加了一个商店")
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException {
        if (shop == null) {
            return new ShopExecution(ShopStatusEnum.NULL_SHOP_INFO);
        }
        try {
            //初始化一些值
            shop.setEnableStatus(0);
            shop.setPriority(1);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            shop.setAdvice(ShopStatusEnum.CHECK.getStateInfo());
            //先保存进入数据库
            int shopId = shopDao.insertShop(shop);
            if (shopId > 0) {
                //保存图片
                String addr = ImageUtil.saveShopImg(shop.getShopId(), shopImgInputStream, fileName);
                //更新图片地址
                shop.setShopImg(addr);
                shopDao.updateShop(shop);
                return new ShopExecution(ShopStatusEnum.CHECK);
            } else {
                return new ShopExecution(ShopStatusEnum.INNER_ERROR);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ShopOperationException("添加商店信息时出现错误，" + ex.getMessage());
        }
    }

    @Override
    public Shop findShopById(Long shopId) {
        return shopDao.findShopById(shopId);
    }

    @Override
    public ShopExecution findShopList(Shop shopCondition, List<Long> shopCategoryIds, int pageIndex, int pageSize) {
        int offset = CalculatorPaging.calcRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.findShopList(shopCondition, shopCategoryIds, offset, pageSize);
        int shopCount = shopDao.findShopCount(shopCondition, shopCategoryIds);
        ShopExecution shopExecution = new ShopExecution();
        if (shopList != null) {
            shopExecution.setShopList(shopList);
            shopExecution.setCount(shopCount);
        } else {
            shopExecution.setState(ShopStatusEnum.INNER_ERROR.getState());
        }
        return shopExecution;
    }
}
