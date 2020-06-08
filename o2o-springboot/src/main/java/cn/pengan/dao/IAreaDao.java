package cn.pengan.dao;

import cn.pengan.entity.Area;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface IAreaDao {
    List<Area> findAll();

    Area findById(Long areaId);
}
