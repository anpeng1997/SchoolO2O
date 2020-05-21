package cn.pengan.controller.superadmin.api;

import cn.pengan.dto.Result;
import cn.pengan.dto.ShopCategoryExecution;
import cn.pengan.entity.ShopCategory;
import cn.pengan.enums.ShopCategoryEnum;
import cn.pengan.service.IShopCategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/shopcategory")
@Api("商品类别API")
public class ShopCategoryApiController {

    private final IShopCategoryService shopCategoryService;
    private final ObjectMapper objectMapper;

    public ShopCategoryApiController(ObjectMapper objectMapper, IShopCategoryService shopCategoryService) {
        this.objectMapper = objectMapper;
        this.shopCategoryService = shopCategoryService;
    }

    @GetMapping("")
    @ApiOperation("获得最高级的商店类别（parentId为null的）")
    public Result getShopCategorys() {
        ShopCategoryExecution shopCategoryList = shopCategoryService.findShopCategoryList(new ShopCategory());
        return new Result(true, shopCategoryList);
    }

    @PostMapping("")
    @ApiOperation("添加一个商品类别")
    public Result addShopCategory(String categoryInfo, MultipartFile imgFile) {
        if (categoryInfo == null) {
            return new Result(false, "商品类别数据不能为空", ShopCategoryEnum.DATE_ERROR.getState());
        }
        if (imgFile == null) {
            return new Result(false, "图片不能为空", ShopCategoryEnum.DATE_ERROR.getState());
        }
        try {
            ShopCategory shopCategory = objectMapper.readValue(categoryInfo, ShopCategory.class);
            if (shopCategory.getShopCategoryName() == null) {
                return new Result(false, "商店类别名称不能为空", ShopCategoryEnum.DATE_ERROR.getState());
            }
            ShopCategoryExecution shopCategoryExecution = shopCategoryService.insertShopCategory(shopCategory, imgFile.getInputStream(), imgFile.getOriginalFilename());
            return new Result(true, shopCategoryExecution);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Result(false, "提交的数据有误", ShopCategoryEnum.DATE_ERROR.getState());
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "服务器内部错误", ShopCategoryEnum.INNER_ERROR.getState());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询商店类别")
    public Result getShopCategory(@PathVariable("id") Long id) {
        ShopCategory shopCategory = shopCategoryService.findShopCategoryById(id);
        if (shopCategory == null) {
            return new Result(false, "没有找到ID为：" + id + "的类别");
        }
        return new Result(true, shopCategory);
    }

    @PostMapping("/modify")
    @ApiOperation("修改商店类别")
    public Result modifyShopCategory(String categoryInfo, MultipartFile imgFile) {
        try {
            ShopCategory shopCategory = objectMapper.readValue(categoryInfo, ShopCategory.class);
            ShopCategoryExecution execution = null;
            if (imgFile == null) {
                execution = shopCategoryService.updateShopCategory(shopCategory, null, null);
            } else {
                execution = shopCategoryService.updateShopCategory(shopCategory, imgFile.getInputStream(), imgFile.getOriginalFilename());
            }
            return new Result(true, execution);
        } catch (JsonMappingException jsonMappingException) {
            jsonMappingException.printStackTrace();
            return new Result(false, "提交的数据有误，" + jsonMappingException.getMessage(), ShopCategoryEnum.INNER_ERROR.getState());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "更新数据失败，" + e.getMessage(), ShopCategoryEnum.INNER_ERROR.getState());
        }
    }
}
