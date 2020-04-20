package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.entity.LocalAuth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Enzo Cotter on 2020/4/20.
 */
public class LocalAuthDaoTest extends BaseTest {

    @Autowired
    private ILocalAuthDao dao;

    @Test
    public void findLocalAuth() {
        LocalAuth tom = dao.findLocalAuth("tom", "123123");
        System.out.println(tom);
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
}
