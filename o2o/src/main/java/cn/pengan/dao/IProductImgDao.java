package cn.pengan.dao;

import cn.pengan.entity.ProductImg;

import java.util.List;

public interface IProductImgDao {
    int batchInsertProductImg(List<ProductImg> imgList);

    int batchDeleteImgByProductId(Long productId);
}
