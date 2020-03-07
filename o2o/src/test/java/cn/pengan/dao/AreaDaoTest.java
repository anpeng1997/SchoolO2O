package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private IAreaDao areaDao;

    @Test
    public void findAllTest(){
        List<Area> all = areaDao.findAll();
        for (Area area : all) {
            System.out.println(area);
        }
        Assert.assertEquals(all.size(),4);
    }
}
