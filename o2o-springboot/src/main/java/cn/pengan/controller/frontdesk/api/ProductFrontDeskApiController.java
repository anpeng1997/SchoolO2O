package cn.pengan.controller.frontdesk.api;

import cn.pengan.dao.IProductDao;
import cn.pengan.dto.ProductExecution;
import cn.pengan.dto.Result;
import cn.pengan.entity.Product;
import cn.pengan.entity.Shop;
import cn.pengan.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/frontdesk/product")
@Api("前台商品API")
public class ProductFrontDeskApiController {

    private final IProductService productService;

    public ProductFrontDeskApiController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{shopId}")
    @ApiOperation("根据条件筛选商品")
    public Result getProductList(@PathVariable("shopId") Long ShopId,
                                 @RequestParam(value = "searchKey", required = false) String searchKey,
                                 @RequestParam(value = "productCategoryIds", required = false) Long[] categoryId,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                 @RequestParam(value = "pageIndex", required = false) Integer pageIndex) {
        Product productCondition = new Product();
        productCondition.setShopId(ShopId);
        //该API只能查询上架的商品
        productCondition.setEnableStatus(1);
        if (searchKey != null && !"".equals(searchKey.trim())) {
            productCondition.setProductName(searchKey);
        }
        if (pageSize == null) {
            pageSize = 100;
        }
        if (pageIndex == null) {
            pageIndex = 0;
        }
        ProductExecution execution = productService.findProductList(productCondition, categoryId, pageIndex, pageSize);
        return new Result(true, execution);
    }
}
