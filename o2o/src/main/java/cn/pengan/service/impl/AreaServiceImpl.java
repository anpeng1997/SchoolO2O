package cn.pengan.service.impl;

import cn.pengan.dao.IAreaDao;
import cn.pengan.entity.Area;
import cn.pengan.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements IAreaService {
   private final IAreaDao areaDao;

    public AreaServiceImpl(IAreaDao areaDao) {
        this.areaDao = areaDao;
    }

    @Override
    public List<Area> findAll() {
        return areaDao.findAll();
    }
}
