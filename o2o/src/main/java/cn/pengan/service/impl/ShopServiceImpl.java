package cn.pengan.service.impl;

import cn.pengan.dao.IShopDao;
import cn.pengan.dto.ShopExecution;
import cn.pengan.entity.Shop;
import cn.pengan.enums.ShopStatusEnum;
import cn.pengan.service.IShopService;
import cn.pengan.util.FileUtil;
import cn.pengan.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

@Service
public class ShopServiceImpl implements IShopService {

    @Autowired
    private IShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, File shopImg) {
        if (shop == null) {
            return new ShopExecution(ShopStatusEnum.NULL_SHOP_INFO);
        }
        try {
            //初始化一些值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            shop.setAdvice(ShopStatusEnum.CHECK.getStateInfo());
            //先保存进入数据库
            int shopId = shopDao.insertShop(shop);
            if (shopId > 0) {
                try {
                    //保存图片
                    String addr = saveImg(shop, shopImg);
                    //更新数据库
                    shop.setShopAddr(addr);
                    shopDao.updateShop(shop);
                } catch (Exception e) {
                    throw new RuntimeException("保存图片失败，"+e.getMessage());
                }
                return new ShopExecution(ShopStatusEnum.CHECK, shop);
            }
            return new ShopExecution(ShopStatusEnum.NULL_SHOP_ID);
        } catch (Exception ex) {
            throw new RuntimeException("保存商店信息失败，" + ex.getMessage());
        }
    }

    /*
     * 保持图片在本地，并把相对路径返回
     * */
    private String saveImg(Shop shop, File file) {
        //先获取shop图片的相对路径
        String relative = FileUtil.getShopImgPath(shop.getShopId());
        //生成缩略图后返回（相对路径加上文件名）
        String path = ImageUtil.generateThumbnail(file, relative);
        return path;
    }
}
