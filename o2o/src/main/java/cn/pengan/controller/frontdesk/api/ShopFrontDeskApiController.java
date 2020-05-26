package cn.pengan.controller.frontdesk.api;

import cn.pengan.dto.Result;
import cn.pengan.dto.ShopCategoryExecution;
import cn.pengan.entity.Shop;
import cn.pengan.entity.ShopCategory;
import cn.pengan.service.IShopCategoryService;
import cn.pengan.service.IShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{parentId}")
    @ApiOperation("获得前台展示的商店列表")
    public Result getShopList(@PathVariable("parentId") Long parentId,
                              @RequestParam(name = "searchKey", required = false) String searchKey,
                              @RequestParam(name = "shopCategoryIds", required = false) List<Long> shopCategorys) {
        ShopCategory shopCategoryCondition = new ShopCategory();
        shopCategoryCondition.setParentId(parentId);
        ShopCategoryExecution shopCategoryList = shopCategoryService.findShopCategoryList(shopCategoryCondition);
        Shop shopCondition = new Shop();
        shopCondition.setShopName(searchKey);
        shopCondition.setParentCategoryId(parentId);
        List<Shop> shopList = shopService.findShopList(shopCondition, shopCategorys, 0, 100).getShopList();
        Map<String, Object> data = new HashMap<>();
        data.put("shopCategoryList", shopCategoryList.getShopCategoryList());
        data.put("shopList", shopList);
        return new Result(true, data);
    }
}
