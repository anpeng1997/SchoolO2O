package cn.pengan.controller.superadmin.api;

import cn.pengan.dto.ProductCategoryExecution;
import cn.pengan.dto.Result;
import cn.pengan.entity.ProductCategory;
import cn.pengan.enums.ProductCategoryStatusEnum;
import cn.pengan.service.IProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        ProductCategoryExecution execution = productCategoryService.findProductCategoryList(id);
        return new Result(true, execution.getProductCategories());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据商品类别ID获取商品类型信息")
    public Result getProductCategory(@PathVariable("id") Long id) {
        ProductCategoryExecution execution = productCategoryService.findProductCategory(id);
        return new Result(true, execution.getProductCategory());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加一个商品类别")
    public Result addProductCategory(@RequestBody ProductCategory productCategory) {
        if (productCategory == null || productCategory.getProductCategoryName() == null || productCategory.getShopId() == null) {
            return new Result(false, "数据不完整", -1001);
        }
        try {
            ProductCategoryExecution execution = productCategoryService.insertProductCategory(productCategory);
            return new Result(true, execution.getStateInfo(), execution.getState());
        } catch (Exception ex) {
            return new Result(false, ex.getMessage(), ProductCategoryStatusEnum.FAIL.getStatus());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据商品类型id删除商品类型")
    public Result deleteProductCategory(@PathVariable("id") Long id) {
        try {
            ProductCategoryExecution execution = productCategoryService.deleteProductCategoryById(id);
            return new Result(true, execution.getStateInfo(), execution.getState());
        } catch (Exception ex) {
            return new Result(false, ex.getMessage(), ProductCategoryStatusEnum.FAIL.getStatus());
        }
    }


    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @ApiOperation(value = "更新商品类别信息")
    public Result editProductCategory(@RequestBody ProductCategory productCategory) {
        try {
            ProductCategoryExecution execution = productCategoryService.updateProductCategory(productCategory);
            return new Result(true, execution.getStateInfo(), execution.getState());
        } catch (Exception ex) {
            return new Result(false, ex.getMessage(), ProductCategoryStatusEnum.FAIL.getStatus());
        }
    }
}
