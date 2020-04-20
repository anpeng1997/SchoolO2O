package cn.pengan.controller.superadmin.api;

import cn.pengan.dto.ProductExecution;
import cn.pengan.dto.Result;
import cn.pengan.entity.Product;
import cn.pengan.enums.ProductStatusEnum;
import cn.pengan.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/product")
@Api(value = "商品API")
public class ProductApiController {

    private static final Logger logger = LoggerFactory.getLogger(ProductApiController.class);

    @Autowired
    private IProductService productService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation("添加商品,默认将上传图片中的第一张图片设置为商品的缩略图")
    public Result addProduct(String productInfo, MultipartFile[] productImgs) {
        if (productImgs.length <= 0) {
            return new Result(false, new ProductExecution(ProductStatusEnum.IMAGE_EMPTY));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Result result;
        try {
            Product product = objectMapper.readValue(productInfo, Product.class);
            Map<String, InputStream> imageMap = new LinkedHashMap<>();
            for (MultipartFile productImg : productImgs) {
                imageMap.put(productImg.getOriginalFilename(), productImg.getInputStream());
            }
            ProductExecution productExecution = productService.insertProduct(product, imageMap);
            result = new Result(true, productExecution);
        } catch (Exception ex) {
            logger.error("【{}】中的【addProduct】出现错误,{}", ProductApiController.class.getName(), ex.getMessage());
            result = new Result(false, new ProductExecution(ProductStatusEnum.INNER_ERROR));
            ex.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation("通过productId获取product")
    public Result getProduct(@PathVariable("id") Long id, HttpServletRequest request) {
        ProductExecution product = productService.findProductById(id);
        return new Result(true, product);
    }

    @PostMapping(value = "/edit")
    @ApiOperation("修改商品信息")
    public Result modifyProduct(String productInfo, MultipartFile[] productImgs) {
        Result result;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, InputStream> imgMap = new LinkedHashMap<>();
            Product product = objectMapper.readValue(productInfo, Product.class);
            for (MultipartFile productImg : productImgs) {
                imgMap.put(productImg.getOriginalFilename(), productImg.getInputStream());
            }
            ProductExecution execution = productService.updateProduct(product, imgMap);
            result = new Result(true, execution);
        } catch (Exception ex) {
            logger.error("【{}】中的【modifyProduct】出现错误,{}", ProductApiController.class.getName(), ex.getMessage());
            result = new Result(false, new ProductExecution(ProductStatusEnum.INNER_ERROR));
            ex.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/paginationproduct", method = RequestMethod.GET)
    @ApiOperation("分页获取product列表")
    public Result getProductList(@RequestParam("shopid") Long shopId,
                                 @RequestParam(value = "pageindex", required = false) Integer pageindex,
                                 @RequestParam(value = "pagesize", required = false) Integer pagesize) {
        if (pageindex == null) {
            pageindex = 0;
        }
        if (pagesize == null) {
            pagesize = 100;
        }
        ProductExecution execution = productService.findProductList(shopId, pageindex, pagesize);
        return new Result(true, execution);
    }

    @PutMapping("/status/{id}")
    @ApiOperation("修改商品的状态")
    public Result changeProductStatus(@PathVariable("id") Long id) {
        Result result;
        try {
            ProductExecution execution = productService.changeProductStatus(id);
            result = new Result(true, execution);
        } catch (Exception ex) {
            logger.error("【{}】中的【changeProductStatus】出现错误,{}", ProductApiController.class.getName(), ex.getMessage());
            result = new Result(false, new ProductExecution(ProductStatusEnum.INNER_ERROR));
            ex.printStackTrace();
        }
        return result;
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("通过商品id来删除商品")
    public Result deleteProduct(@PathVariable("id") Long id){
        return new Result();
    }
}
