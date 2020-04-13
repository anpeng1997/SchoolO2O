package cn.pengan.service;

import cn.pengan.BaseTest;
import cn.pengan.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaServiceTest extends BaseTest {

    @Autowired
    private IAreaService areaService;

    @Test
    public void findAllTest() {
        List<Area> all = areaService.findAll();
        Assert.assertEquals(all.size(), 4);
    }
}
