package cn.pengan.dao;

import cn.pengan.entity.ProductImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IProductImgDao {
    int batchInsertProductImg(List<ProductImg> imgList);

    int batchDeleteImgByProductId(Long productId);
}
