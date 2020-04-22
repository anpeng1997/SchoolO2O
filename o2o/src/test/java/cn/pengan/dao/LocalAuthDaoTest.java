package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.entity.LocalAuth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class LocalAuthDaoTest extends BaseTest {

    @Autowired
    private ILocalAuthDao dao;

    @Test
    public void findLocalAuth() {
        LocalAuth tom = dao.findLocalAuth("tom", "123456");
        System.out.println(tom.getPersonInfo());
    }

    @Test
    public void insertLocalAuth() {
        LocalAuth localAuth = new LocalAuth();
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        localAuth.setUserId(1L);
        localAuth.setPassword("123123");
        localAuth.setUserName("jerry");
        dao.insertLocalAuth(localAuth);
        System.out.println(localAuth.getLocalAuthId());
    }

    @Test
    public void Test() {
        System.out.println( 5<< 3);
    }
}
