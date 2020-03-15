package cn.pengan.controller.superadmin.api;

import cn.pengan.dto.Result;
import cn.pengan.entity.ProductCategory;
import cn.pengan.entity.Shop;
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

    @RequestMapping(value = "/getlist/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "商店的所有商品类别", notes = "根据商店ID获取所有的商品类别")
    public Map<String, Object> getProductCategoryList(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        if (id == null) {
            result.put("success", false);
            result.put("errorMsg", "参数ID不能为空");
            return result;
        }
        List<ProductCategory> productCategoryList = productCategoryService.findProductCategoryList(id);
        result.put("success", true);
        result.put("data", productCategoryList);
        return result;
    }

    @RequestMapping(value = "/addcategory", method = RequestMethod.POST)
    @ApiOperation(value = "添加一个商品类别")
    public Result addProductCategory(@RequestBody ProductCategory productCategory) {
        if (productCategory == null || productCategory.getProductCategoryName() == null || productCategory.getShopId() == null) {
            return new Result(false, "数据不完整", -1001);
        }
        int affectRowNum = productCategoryService.insertProductCategory(productCategory);
        return new Result(true, affectRowNum);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据商品类型id删除商品类型")
    public Result deleteProductCategory(@PathVariable("id") Long id) {
        int affectedRowNum = productCategoryService.deleteProductCategoryById(id);
        return new Result(true, affectedRowNum);
    }
}
