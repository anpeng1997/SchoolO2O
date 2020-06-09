package cn.pengan.dao;

import cn.pengan.entity.ProductImg;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductImgDao {
    int batchInsertProductImg(List<ProductImg> imgList);

    int batchDeleteImgByProductId(Long productId);
}
