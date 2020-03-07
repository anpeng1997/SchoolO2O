package cn.pengan.service;

import cn.pengan.dto.ShopExecution;
import cn.pengan.entity.Shop;
import cn.pengan.exceptions.ShopOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IShopService {

    ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

    ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

    Shop findShopById(Long shopId);

    ShopExecution findShopList(Shop shopCondition,int pageIndex,int pageSize);

}
