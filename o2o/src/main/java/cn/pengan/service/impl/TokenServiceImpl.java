package cn.pengan.service.impl;

import cn.pengan.cache.JedisUtil;
import cn.pengan.entity.PersonInfo;
import cn.pengan.service.ITokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class TokenServiceImpl implements ITokenService {

    private final String HEADER_TOKEN_KEY = "Authenticate-Token";

    @Value("${redis.user.session.key}")
    private String REDIS_SESSION_KEY;

    @Value("${redis.user.session.expired}")
    private int REDIS_SESSION_EXPIRED;

    private final ObjectMapper objectMapper;

    private final JedisUtil.Strings jedisString;

    private final JedisUtil jedisUtil;

    public TokenServiceImpl(ObjectMapper objectMapper, JedisUtil.Strings jedisString, JedisUtil jedisUtil) {
        this.objectMapper = objectMapper;
        this.jedisString = jedisString;
        this.jedisUtil = jedisUtil;
    }

    @Override
    public String generateAuthenticateToken(Object userInfo) {
        if (userInfo == null) {
            return null;
        }
        //使用UUD生成唯一的token
        String token = UUID.randomUUID().toString().replace("-", "");
        try {
            String json = objectMapper.writeValueAsString(userInfo);
            //存入redis中
            String status = jedisString.setEx(REDIS_SESSION_KEY + ":" + token, json, REDIS_SESSION_EXPIRED);
            if ("OK".equals(status)) {
                return token;
            }
            return null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PersonInfo getPersonByToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_TOKEN_KEY);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String personJson = jedisString.get(REDIS_SESSION_KEY + ":" + token);
        if (StringUtils.isEmpty(personJson)) {
            return null;
        }
        //获取成功后，重置一下该Token的过期时间
        jedisUtil.expire(REDIS_SESSION_KEY + ":" + token, REDIS_SESSION_EXPIRED);
        try {
            PersonInfo personInfo = objectMapper.readValue(personJson, PersonInfo.class);
            return personInfo;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
