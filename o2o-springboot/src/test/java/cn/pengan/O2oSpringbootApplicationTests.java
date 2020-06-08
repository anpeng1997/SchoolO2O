package cn.pengan;

import cn.pengan.dao.IAreaDao;
import cn.pengan.entity.Area;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class O2oSpringbootApplicationTests {

    @Autowired
    private IAreaDao areaDao;

    @Test
    void contextLoads() {
        List<Area> all = areaDao.findAll();
        all.forEach(System.out::println);
    }

}
