package cn.pengan.controller.superadmin.api;

import cn.pengan.cache.JedisUtil;
import cn.pengan.dto.Result;
import cn.pengan.dto.ShopCategoryExecution;
import cn.pengan.dto.ShopExecution;
import cn.pengan.entity.Area;
import cn.pengan.entity.PersonInfo;
import cn.pengan.entity.Shop;
import cn.pengan.entity.ShopCategory;
import cn.pengan.enums.ShopStatusEnum;
import cn.pengan.service.IAreaService;
import cn.pengan.service.IShopCategoryService;
import cn.pengan.service.IShopService;
import cn.pengan.service.ITokenService;
import cn.pengan.util.CodeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@Api(value = "商铺API")
@RequestMapping("/api/shop")
public class ShopApiController {
    private final IShopService shopService;
    private final IShopCategoryService shopCategoryService;
    private final IAreaService areaService;
    private final ObjectMapper objectMapper;
    private final ITokenService tokenService;


    private static final Logger logger = LoggerFactory.getLogger(ShopApiController.class);

    public ShopApiController(IShopService shopService,
                             IAreaService areaService,
                             ObjectMapper objectMapper,
                             @Qualifier("shopCategoryService") IShopCategoryService shopCategoryService,
                             ITokenService tokenService) {
        this.shopService = shopService;
        this.areaService = areaService;
        this.objectMapper = objectMapper;
        this.shopCategoryService = shopCategoryService;
        this.tokenService = tokenService;
    }

    @RequestMapping(value = "/operationinitdata", method = RequestMethod.GET)
    @ApiOperation(value = "操作商铺所需的初始数据")
    public Result operationShopInitData() {
        Map<String, Object> data = new HashMap<>();
        ShopCategoryExecution execution = shopCategoryService.findShopCategoryList(new ShopCategory());
        List<Area> areaList = areaService.findAll();
        data.put("shopCategoryList", execution.getShopCategoryList());
        data.put("areaList", areaList);
        return new Result(true, data);
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ApiOperation(value = "注册商铺")
    public Result shopRegister(String shopInfo, HttpServletRequest request,
                               @RequestParam("shopImg") MultipartFile shopImg) throws JsonProcessingException {
        boolean isVerify = CodeUtil.checkVerifyCode(request);
//        if (!isVerify) {
//            return new Result(false, "验证码错误!", ShopStatusEnum.NULL_SHOP_INFO.getState());
//        }
        if (shopImg.isEmpty()) {
            return new Result(false, "商店图片不能为空！", ShopStatusEnum.NULL_SHOP_INFO.getState());
        }
        Shop shopEntity = objectMapper.readValue(shopInfo, Shop.class);
        if (shopEntity.getShopName() == null || "".equals(shopEntity.getShopName().trim())) {
            return new Result(false, "商店图片不能为空！", ShopStatusEnum.NULL_SHOP_INFO.getState());
        }
        Result result;
        PersonInfo personInfo = tokenService.getPersonByToken(request);
        shopEntity.setOwner(personInfo);
        try {
            ShopExecution shopExecution = shopService.addShop(shopEntity, shopImg.getInputStream(), shopImg.getOriginalFilename());
            result = new Result(true, shopExecution);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "服务器内部错误", ShopStatusEnum.INNER_ERROR.getState());
        }
        return result;
    }

    @RequestMapping(value = "/modify", method = {RequestMethod.POST})
    @ApiOperation(value = "修改商铺信息")
    public Result shopModify(String shopInfo, HttpServletRequest request, MultipartFile shopImg) throws JsonProcessingException {
        boolean isVerify = CodeUtil.checkVerifyCode(request);
        if (!isVerify) {
            return new Result(false, "验证码错误！");
        }
        Shop shop = objectMapper.readValue(shopInfo, Shop.class);
        ShopExecution shopExecution;
        Result result;
        try {
            if (shopImg == null) {
                //没有上传图片
                shopExecution = shopService.modifyShop(shop, null, null);
            } else {
                shopExecution = shopService.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
            }
            result = new Result(true, shopExecution);
        } catch (IOException e) {
            result = new Result(false, "服务器内部错误", ShopStatusEnum.INNER_ERROR.getState());
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据商铺ID来获取商铺信息")
    public Result getShopById(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Shop shop = shopService.findShopById(id);
        if (shop == null) {
            return new Result(false, "没有查询到ID：" + id + " 的店铺");
        }
        return new Result(true, shop);
    }

    @RequestMapping(value = "/paginationshop", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取商店数据", notes = "当不传入分页条件时，默认返回从0开始的100条数据")
    public Result findShopList(@ApiParam(value = "页面索引")
                               @RequestParam(value = "pageindex", required = false) Integer pageindex,
                               @ApiParam(value = "页面数据大小")
                               @RequestParam(value = "pagesize", required = false) Integer pagesize,
                               HttpServletRequest request) {
        PersonInfo personInfo = tokenService.getPersonByToken(request);
        Shop shopCondition = new Shop();
        shopCondition.setOwner(personInfo);
        if (pageindex == null) {
            pageindex = 0;
        }
        if (pagesize == null) {
            pagesize = 100;
        }
        try {
            ShopExecution shopList = shopService.findShopList(shopCondition, null, pageindex, pagesize);
            return new Result(true, shopList);
        } catch (Exception ex) {
            return new Result(false, "查询数据错误", ShopStatusEnum.INNER_ERROR.getState());
        }
    }
}
