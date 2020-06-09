package cn.pengan.dao;

import cn.pengan.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAreaDao {
    List<Area> findAll();

    Area findById(Long areaId);
}
