package cn.pengan.controller.superadmin.api;

import cn.pengan.dto.Result;
import cn.pengan.entity.ProductCategory;
import cn.pengan.enums.ProductCategoryExecutionEnum;
import cn.pengan.service.IProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productcategory")
@Api(value = "商店类别")
public class ProductCategoryApiController {

    private final IProductCategoryService productCategoryService;

    public ProductCategoryApiController(IProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "商店的所有商品类别", notes = "根据商店ID获取所有的商品类别")
    public Result getProductCategoryList(@PathVariable("id") Long id) {
        List<ProductCategory> productCategoryList = productCategoryService.findProductCategoryList(id);
        return new Result(true,productCategoryList);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加一个商品类别")
    public Result addProductCategory(@RequestBody ProductCategory productCategory) {
        if (productCategory == null || productCategory.getProductCategoryName() == null || productCategory.getShopId() == null) {
            return new Result(false, "数据不完整", -1001);
        }
        ProductCategoryExecutionEnum result = productCategoryService.insertProductCategory(productCategory);
        return new Result(true, result.getStatusInfo(), result.getStatus());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据商品类型id删除商品类型")
    public Result deleteProductCategory(@PathVariable("id") Long id) {
        ProductCategoryExecutionEnum result = productCategoryService.deleteProductCategoryById(id);
        return new Result(true, result.getStatusInfo(), result.getStatus());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据商品类别ID获取商品类型信息")
    public Result getProductCategory(@PathVariable("id") Long id) {
        ProductCategory productCategory = productCategoryService.findProductCategory(id);
        return new Result(true, productCategory);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @ApiOperation(value = "更新商品类别信息")
    public Result editProductCategory(@RequestBody ProductCategory productCategory) {
        ProductCategoryExecutionEnum result = productCategoryService.updateProductCategory(productCategory);
        return new Result(true, result.getStatusInfo(), result.getStatus());
    }
}
