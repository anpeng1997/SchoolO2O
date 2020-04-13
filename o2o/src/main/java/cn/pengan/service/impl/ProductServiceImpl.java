package cn.pengan.service.impl;

import cn.pengan.dao.IProductDao;
import cn.pengan.dao.IProductImgDao;
import cn.pengan.dto.ProductExecution;
import cn.pengan.entity.Product;
import cn.pengan.entity.ProductImg;
import cn.pengan.enums.ProductStatusEnum;
import cn.pengan.exceptions.ProductOperationException;
import cn.pengan.service.IProductService;
import cn.pengan.util.CalculatorPaging;
import cn.pengan.util.FileUtil;
import cn.pengan.util.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {
    private final IProductDao productDao;
    private final IProductImgDao productImgDao;

    public ProductServiceImpl(IProductDao productDao, IProductImgDao productImgDao) {
        this.productDao = productDao;
        this.productImgDao = productImgDao;
    }

    @Transactional
    @Override
    public ProductExecution insertProduct(Product product, Map<String, InputStream> productImgs) throws ProductOperationException {
        try {
            if (productImgs.size() <= 0) {
                return new ProductExecution(ProductStatusEnum.IMAGE_EMPTY);
            }
            product.setPoint(0);
            product.setLastEditTime(new Date());
            product.setCreateTime(new Date());
            product.setEnableStatus(1);
            //保存数据至数据库
            productDao.insertProduct(product);
            Long productId = product.getProductId();
            //保存缩略图,将上传的图片中的第一张图片更新至上面添加product imgAddr中
            //获取map中的第一个值
            String firstImgKey = productImgs.keySet().stream().findFirst().get();
            InputStream firstInputStreamImg = productImgs.get(firstImgKey);
            String productImgRelativePath = ImageUtil.saveProductImg(productId, firstInputStreamImg, firstImgKey);
            product.setImgAddr(productImgRelativePath);
            //更新缩略图相对路径
            productDao.updateProduct(product);
            //插入图片集合进入数据库
            if (productImgs.size() > 1) {
                //保存完后移除第一张图
                productImgs.remove(firstImgKey);
                //保存第一张图片后面的所有图片
                List<ProductImg> imgObjs = saveImgs(productId, productImgs);
                productImgDao.batchInsertProductImg(imgObjs);
            }
            return new ProductExecution(ProductStatusEnum.SUCCESS);
        } catch (Exception ex) {
            throw new ProductOperationException("插入商品数据出现错误，" + ex.getMessage());
        }
    }

    @Transactional
    @Override
    public ProductExecution updateProduct(Product product, Map<String, InputStream> productImgs) throws ProductOperationException {
        try {
            //判断是否上传了新的图片
            if (productImgs.size() > 0) {
                Long productId = product.getProductId();
                //删除所有原来的图片
                String oldProductImgPath = FileUtil.getProductImgPath(productId);
                productImgDao.batchDeleteImgByProductId(productId);
                FileUtil.deleteFileOrDirectory(oldProductImgPath);
                //第一张图片为头像
                String firstImgKey = productImgs.keySet().stream().findFirst().get();
                InputStream firstInput = productImgs.get(firstImgKey);
                String newProductImgPath = ImageUtil.saveProductImg(productId, firstInput, firstImgKey);
                product.setImgAddr(newProductImgPath);
                if (productImgs.size() > 1) {  //表示上传了其它的详情图
                    productImgs.remove(firstImgKey); //从map中移除第一项
                    List<ProductImg> newImgList = saveImgs(productId, productImgs);
                    productImgDao.batchInsertProductImg(newImgList);
                }
            }
            productDao.updateProduct(product);
            return new ProductExecution(ProductStatusEnum.SUCCESS);
        } catch (Exception ex) {
            throw new ProductOperationException("更新商品数据出现错误，" + ex.getMessage());
        }
    }

    @Override
    public ProductExecution findProductById(Long productId) {
        Product product = productDao.findProductById(productId);
        return new ProductExecution(ProductStatusEnum.SUCCESS, product);
    }

    @Override
    public ProductExecution findProductList(Long shopId, int pageIndex, int pageSize) {
        int offset = CalculatorPaging.calcRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.findProductList(shopId, offset, pageSize);
        int productCount = productDao.findProductCount(shopId);
        ProductExecution execution = new ProductExecution(ProductStatusEnum.SUCCESS, productList);
        execution.setCount(productCount);
        return execution;
    }

    @Override
    public ProductExecution changeProductStatus(Long productId) throws ProductOperationException {
        Product product = productDao.findProductById(productId);
        try {
            Integer newStatus = product.getEnableStatus() == 1 ? 0 : 1;
            product.setEnableStatus(newStatus);
            productDao.updateProduct(product);
            return new ProductExecution(ProductStatusEnum.SUCCESS);
        } catch (Exception ex) {
            throw new ProductOperationException("修改商品状态出现异常," + ex.getMessage());
        }
    }

    /**
     * 保存图片集合
     *
     * @param productId
     * @param imgs
     * @return ProductImg对象集合
     * @throws IOException
     */
    private List<ProductImg> saveImgs(Long productId, Map<String, InputStream> imgs) throws IOException {
        List<ProductImg> result = new ArrayList<>();
        for (String key : imgs.keySet()) {  //遍历所有的图片进行添加
            InputStream inputStream = imgs.get(key);
            String newPath = ImageUtil.saveProductImg(productId, inputStream, key);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(newPath);
            productImg.setProductId(productId);
            productImg.setCreateTime(new Date());
            result.add(productImg);
        }
        return result;
    }
}
