package cn.pengan.controller.superadmin.api;

import cn.pengan.controller.superadmin.ShopController;
import cn.pengan.dto.ShopExecution;
import cn.pengan.entity.Area;
import cn.pengan.entity.PersonInfo;
import cn.pengan.entity.Shop;
import cn.pengan.entity.ShopCategory;
import cn.pengan.enums.ShopStatusEnum;
import cn.pengan.service.IAreaService;
import cn.pengan.service.IShopCategoryService;
import cn.pengan.service.IShopService;
import cn.pengan.util.CodeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "商铺API")
@RequestMapping("/api/shop")
public class ShopApiController {
    private final IShopService shopService;
    private final IShopCategoryService shopCategoryService;
    private final IAreaService areaService;

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    public ShopApiController(IShopService shopService, IShopCategoryService shopCategoryService, IAreaService areaService) {
        this.shopService = shopService;
        this.shopCategoryService = shopCategoryService;
        this.areaService = areaService;
    }

    @RequestMapping(value = "/operationinitdata", method = RequestMethod.GET)
    @ApiOperation(value = "操作商铺所需的初始数据")
    public Map<String, Object> operationShopInitData() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ShopCategory> shopCategoryList = shopCategoryService.findShopCategoryList(new ShopCategory());
            List<Area> areaList = areaService.findAll();
            result.put("success", true);
            result.put("shopCategoryList", shopCategoryList);
            result.put("areaList", areaList);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("errorMsg", ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ApiOperation(value = "注册商铺")
    public Map<String, Object> shopRegister(String shopInfo, HttpServletRequest request,
                                            @RequestParam("shopImg") MultipartFile shopImg) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        boolean isVerify = CodeUtil.checkVerifyCode(request);
        if (!isVerify) {
            result.put("success", false);
            result.put("errorMsg", "验证码错误！");
            return result;
        }
        if (shopImg.isEmpty()) {
            result.put("success", false);
            result.put("errorMsg", "商店图片不能为空！");
            return result;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shopEntity = objectMapper.readValue(shopInfo, Shop.class);
        if (shopEntity.getShopName() == null || "".equals(shopEntity.getShopName().trim())) {
            result.put("success", false);
            result.put("errorMsg", "商店名不能为!");
        }
        //TODO:从session中获取owner_id;
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        shopEntity.setOwner(personInfo);
        ShopExecution shopExecution = null;
        try {
            shopExecution = shopService.addShop(shopEntity, shopImg.getInputStream(), shopImg.getOriginalFilename());
            result.put("success", true);
            result.put("data", shopExecution);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("errorMsg", "保存图片失败");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("errorMsg", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/modify", method = {RequestMethod.POST})
    @ApiOperation(value = "修改商铺信息")
    public Map<String, Object> shopModify(String shopInfo, HttpServletRequest request, MultipartFile shopImg) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        boolean isVerify = CodeUtil.checkVerifyCode(request);
        if (!isVerify) {
            result.put("success", false);
            result.put("errorMsg", "验证码错误！");
            return result;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = objectMapper.readValue(shopInfo, Shop.class);
        ShopExecution shopExecution;
        try {
            if (shopImg == null) {
                //没有上传图片
                shopExecution = shopService.modifyShop(shop, null, null);
            } else {
                shopExecution = shopService.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
            }
            result.put("success", true);
            result.put("data", shopExecution);
        } catch (IOException e) {
            result.put("success", false);
            result.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据商铺ID来获取商铺信息")
    public Map<String, Object> getShopById(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Shop shop = shopService.findShopById(id);
        if (shop == null) {
            result.put("success", false);
            result.put("errorMsg", "没有查询到ID：" + id + " 的店铺");
            return result;
        }
        result.put("success", true);
        result.put("data", shop);
        return result;
    }

    @RequestMapping(value = "/paginationshop", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取商店数据", notes = "当不传入分页条件时，默认返回从0开始的100条数据")
    public Map<String, Object> findShopList(@ApiParam(value = "页面索引")
                                            @RequestParam(value = "pageindex",required = false) Integer pageindex,
                                            @ApiParam(value = "页面数据大小")
                                            @RequestParam(value = "pagesize",required = false) Integer pagesize, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        //该api是用来做店铺列表的展示的，所以只根据owner_id来查询
        // TODO: 查询真实的session中user
        PersonInfo currentUser = new PersonInfo();
        currentUser.setUserId(1L);
        currentUser.setName("adminTEST");
        request.getSession().setAttribute("user", currentUser);
        Shop shopCondition = new Shop();
        shopCondition.setOwner(currentUser);
        if (pageindex == null) {
            pageindex = 0;
        }
        if (pagesize == null) {
            pagesize = 100;
        }
        try {
            ShopExecution shopList = shopService.findShopList(shopCondition, pageindex, pagesize);
            result.put("success", true);
            result.put("data", shopList);
            result.put("currentUser", currentUser);
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("success", false);
            result.put("errorMsg", "查询数据错误，" + ex.getMessage());
        }
        return result;
    }
}
