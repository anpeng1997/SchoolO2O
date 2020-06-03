package cn.pengan.controller.frontdesk.api;

import cn.pengan.dto.ProductCategoryExecution;
import cn.pengan.dto.Result;
import cn.pengan.dto.ShopCategoryExecution;
import cn.pengan.dto.ShopExecution;
import cn.pengan.entity.Area;
import cn.pengan.entity.Shop;
import cn.pengan.entity.ShopCategory;
import cn.pengan.service.IAreaService;
import cn.pengan.service.IProductCategoryService;
import cn.pengan.service.IShopCategoryService;
import cn.pengan.service.IShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private final IAreaService areaService;
    private final IProductCategoryService productCategoryService;

    public ShopFrontDeskApiController(IShopCategoryService shopCategoryService,
                                      IShopService shopService,
                                      IAreaService areaService,
                                      IProductCategoryService productCategoryService) {
        this.shopCategoryService = shopCategoryService;
        this.shopService = shopService;
        this.areaService = areaService;
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/shopcategorys/{parentId}")
    @ApiOperation("根据一级类别ID获取二级类别列表,还有区域列表")
    public Result getShopList(@PathVariable("parentId") Long parentId) {
        ShopCategory shopCategoryCondition = new ShopCategory();
        shopCategoryCondition.setParentId(parentId);
        ShopCategoryExecution shopCategory = shopCategoryService.findShopCategoryList(shopCategoryCondition);
        List<Area> areas = areaService.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("shopCategoryList", shopCategory.getShopCategoryList());
        data.put("areas", areas);
        return new Result(true, data);
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

    @GetMapping("/shopdetail/{shopId}")
    @ApiOperation("获得商店详情页数据")
    public Result getShopDetailData(@PathVariable("shopId") Long shopId) {
        Shop shop = shopService.findShopById(shopId);
        ProductCategoryExecution productCategoryExecution = productCategoryService.findProductCategoryList(shopId);
        Map<String, Object> data = new HashMap<>();
        data.put("shop", shop);
        data.put("productCategoryList", productCategoryExecution.getProductCategories());
        return new Result(true, data);
    }
}
