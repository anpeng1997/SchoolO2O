package cn.pengan.serviceTest;

import cn.pengan.entity.Area;
import cn.pengan.service.IAreaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AreaServiceImplTest {

    @Autowired
    private IAreaService areaService;

    @Test
    void findAllTest() {
        List<Area> all = areaService.findAll();
        int i = 1/0;
        all.forEach(System.out::println);
    }
}
