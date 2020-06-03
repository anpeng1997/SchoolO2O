package cn.pengan.service;

import cn.pengan.BaseTest;
import cn.pengan.cache.JedisUtil;
import cn.pengan.entity.LocalAuth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenServiceTest extends BaseTest {
    @Autowired
    private JedisUtil.Strings strings;

    @Autowired
    private ITokenService tokenService;

    @Test
    public void getToken() {
//        LocalAuth localAuth = new LocalAuth();
//        localAuth.setUserName("test");
//        localAuth.setPassword("password");
//        String s = tokenService.generateAuthenticateToken(localAuth);
//        System.out.println(s);
    }
}
