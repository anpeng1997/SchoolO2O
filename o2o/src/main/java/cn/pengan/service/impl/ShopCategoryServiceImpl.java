package cn.pengan.service.impl;

import cn.pengan.annotations.DataOperationLog;
import cn.pengan.dao.IShopCategoryDao;
import cn.pengan.dto.ShopCategoryExecution;
import cn.pengan.dto.ShopCategoryPickerModel;
import cn.pengan.entity.ShopCategory;
import cn.pengan.enums.ShopCategoryEnum;
import cn.pengan.exceptions.ShopCategoryOperationException;
import cn.pengan.service.IShopCategoryService;
import cn.pengan.util.FileUtil;
import cn.pengan.util.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service("shopCategoryService")
public class ShopCategoryServiceImpl implements IShopCategoryService {

    private final IShopCategoryDao shopCategoryDao;

    public ShopCategoryServiceImpl(IShopCategoryDao shopCategoryDao) {
        this.shopCategoryDao = shopCategoryDao;
    }

    @Override
    public ShopCategory findShopCategoryById(Long shopCategoryId) {
        return shopCategoryDao.findShopCategoryById(shopCategoryId);
    }

    @Override
    public ShopCategoryExecution updateShopCategory(ShopCategory shopCategory, InputStream inputStream, String fileName) {
        if (shopCategory == null) {
            return new ShopCategoryExecution(ShopCategoryEnum.DATE_ERROR);
        }
        try {
            //先判断是否上传了新的图片
            if (inputStream != null && fileName != null) {
                //上传了新的图片,先根据id查找旧的category
                ShopCategory oldShopCategory = shopCategoryDao.findShopCategoryById(shopCategory.getShopCategoryId());
                if (oldShopCategory == null) {
                    return new ShopCategoryExecution(ShopCategoryEnum.DATE_EMPTY);
                }
                //删除原有的图片
                String oldImgPath = oldShopCategory.getShopCategoryImg();
                FileUtil.deleteFileOrDirectory(oldImgPath);
                //保存当前的上传的图片,并获得新图片的相对路径
                String relativePath = ImageUtil.saveShopCategoryImg(oldShopCategory.getShopCategoryId(), inputStream, fileName);
                shopCategory.setShopCategoryImg(relativePath);
            }
            shopCategory.setLastEditTime(new Date());
            int affectedNum = shopCategoryDao.updateShopCategory(shopCategory);
            if (affectedNum <= 0) {
                return new ShopCategoryExecution(ShopCategoryEnum.FAIL);
            }
            return new ShopCategoryExecution(ShopCategoryEnum.SUCCESS, shopCategory);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ShopCategoryOperationException("modify shop category error," + ex.getMessage());
        }
    }

    @Override
    public ShopCategoryExecution findShopCategoryList(ShopCategory shopCategory) {
        List<ShopCategory> shopCategoryList = shopCategoryDao.findShopCategoryList(shopCategory);
        int count = shopCategoryDao.findCount(shopCategory);
        ShopCategoryExecution shopCategoryExecution = new ShopCategoryExecution(ShopCategoryEnum.SUCCESS, shopCategoryList, count);
        return shopCategoryExecution;
    }

    @Override
    public List<ShopCategoryPickerModel> findShopCategoryPickerList() {
        return shopCategoryDao.findShopCategoryPickerList();
    }

    @DataOperationLog("添加了一个商店类别信息")
    @Transactional
    @Override
    public ShopCategoryExecution insertShopCategory(ShopCategory shopCategory, InputStream categoryImgInput, String fileName) throws ShopCategoryOperationException {

        //先保存数据，在保存图片，最后在更新数据
        try {
            shopCategory.setCreateTime(new Date());
            shopCategory.setLastEditTime(new Date());
            int var1 = shopCategoryDao.insertShopCategory(shopCategory);
            if (var1 <= 0) {
                return new ShopCategoryExecution(ShopCategoryEnum.FAIL);
            }
            //保存图片
            String relativePath = ImageUtil.saveShopCategoryImg(shopCategory.getShopCategoryId(), categoryImgInput, fileName);
            shopCategory.setShopCategoryImg(relativePath);
            //更新数据
            int var2 = shopCategoryDao.updateShopCategory(shopCategory);
            if (var2 <= 0) {
                return new ShopCategoryExecution(ShopCategoryEnum.FAIL);
            }
            return new ShopCategoryExecution(ShopCategoryEnum.SUCCESS, shopCategory);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ShopCategoryOperationException("添加商品类别时出现错误" + ex.getMessage());
        }
    }
}
