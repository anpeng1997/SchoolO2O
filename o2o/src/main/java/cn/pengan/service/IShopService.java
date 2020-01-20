package cn.pengan.service;

import cn.pengan.dto.ShopExecution;
import cn.pengan.entity.Shop;

import java.io.File;

public interface IShopService {

    ShopExecution addShop(Shop shop, File shopImg);
}
