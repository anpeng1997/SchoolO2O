package cn.pengan.dao;

import cn.pengan.dto.ShopCategoryPickerModel;
import cn.pengan.entity.ShopCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IShopCategoryDao {
    ShopCategory findShopCategoryById(Long shopCategoryId);

    List<ShopCategory> findShopCategoryList(ShopCategory category);

    List<ShopCategoryPickerModel> findShopCategoryPickerList();

    int findCount(ShopCategory condition);

    int insertShopCategory(ShopCategory shopCategory);

    int updateShopCategory(ShopCategory shopCategory);
}
