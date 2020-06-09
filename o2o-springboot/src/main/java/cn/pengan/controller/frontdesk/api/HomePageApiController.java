package cn.pengan.controller.frontdesk.api;

import cn.pengan.dto.Result;
import cn.pengan.dto.ShopCategoryExecution;
import cn.pengan.entity.HeadLine;
import cn.pengan.entity.ShopCategory;
import cn.pengan.service.IHeadLineService;
import cn.pengan.service.IShopCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/frontdesk/home")
@Api("前台首页API")
public class HomePageApiController {
    private final IHeadLineService headLineService;
    private final IShopCategoryService shopCategoryService;

    public HomePageApiController(@Qualifier("headLineService") IHeadLineService headLineService, IShopCategoryService shopCategoryService) {
        this.headLineService = headLineService;
        this.shopCategoryService = shopCategoryService;
    }

    @GetMapping("/initdata")
    @ApiOperation("获得首页初始化数据")
    public Result getHomeInitData() {
        ShopCategoryExecution shopCategoryExecution = shopCategoryService.findShopCategoryList(new ShopCategory());
        HeadLine headLine = new HeadLine();
        headLine.setEnableStatus(1);
        List<HeadLine> headLineList = headLineService.findHeadLineList(headLine);
        Map<String, Object> data = new HashMap<>();
        data.put("shopCategoryList", shopCategoryExecution.getShopCategoryList());
        data.put("headLineList", headLineList);
        return new Result(true, data);
    }
}
