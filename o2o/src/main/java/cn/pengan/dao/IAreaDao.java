package cn.pengan.dao;

import cn.pengan.entity.Area;

import java.util.List;

public interface IAreaDao {
    List<Area> findAll();

    Area findById(Long areaId);
}
