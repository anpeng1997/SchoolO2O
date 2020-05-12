package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.cache.JedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisTest extends BaseTest {

    @Autowired
    private JedisUtil.Strings strings;

    @Test
    public void test1() {
        String set = strings.set("test100", "sadasdsad");
        System.out.println(set);
    }
}
