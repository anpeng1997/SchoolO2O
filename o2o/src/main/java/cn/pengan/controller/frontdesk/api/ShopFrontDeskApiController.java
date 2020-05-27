package cn.pengan.controller.frontdesk.api;

import cn.pengan.dto.Result;
import cn.pengan.dto.ShopCategoryExecution;
import cn.pengan.dto.ShopExecution;
import cn.pengan.entity.Area;
import cn.pengan.entity.Shop;
import cn.pengan.entity.ShopCategory;
import cn.pengan.service.IShopCategoryService;
import cn.pengan.service.IShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/frontdesk/shop")
@Api("前台商品类别API")
public class ShopFrontDeskApiController {

    private final IShopCategoryService shopCategoryService;
    private final IShopService shopService;

    public ShopFrontDeskApiController(IShopCategoryService shopCategoryService, IShopService shopService) {
        this.shopCategoryService = shopCategoryService;
        this.shopService = shopService;
    }

    @GetMapping("/shopcategorys/{parentId}")
    @ApiOperation("根据一级类别ID获取二级类别列表")
    public Result getShopList(@PathVariable("parentId") Long parentId) {
        ShopCategory shopCategoryCondition = new ShopCategory();
        shopCategoryCondition.setParentId(parentId);
        ShopCategoryExecution shopCategoryList = shopCategoryService.findShopCategoryList(shopCategoryCondition);
        return new Result(true, shopCategoryList);
    }

    @GetMapping("/{parentId}")
    @ApiOperation("获得前台展示的商店列表")
    public Result getShopList(@PathVariable("parentId") Long parentId,
                              @RequestParam(name = "searchKey", required = false) String searchKey,
                              @RequestParam(name = "shopCategoryIds", required = false) List<Long> shopCategorys,
                              @RequestParam(name = "areaId", required = false) Long areaId,
                              @RequestParam(name = "pageSize", required = false) Integer pageSize,
                              @RequestParam(name = "pageIndex", required = false) Integer pageIndex) {
        Shop shopCondition = new Shop();
        shopCondition.setParentCategoryId(parentId);
        if (!StringUtils.isEmpty(searchKey)) {
            shopCondition.setShopName(searchKey);
        }
        if (areaId != null) {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if (pageSize == null) {
            pageSize = 100;
        }
        if (pageIndex == null) {
            pageIndex = 0;
        }
        ShopExecution shopList = shopService.findShopList(shopCondition, shopCategorys, pageIndex, pageSize);
        return new Result(true, shopList);
    }
}
