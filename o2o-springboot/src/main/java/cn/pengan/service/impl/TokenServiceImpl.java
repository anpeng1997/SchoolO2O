package cn.pengan.service.impl;

import cn.pengan.entity.PersonInfo;
import cn.pengan.service.ITokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.UUID;

@Service
@Deprecated //如今使用的是jwt token验证，已不使用redis来保存当前登录用户的信息了
public class TokenServiceImpl implements ITokenService {

    private final String HEADER_TOKEN_KEY = "Authenticate-Token";

    @Value("${tokensessionkey.key}")
    private String REDIS_SESSION_KEY;

    @Value("${tokensessionkey.expired}")
    private int REDIS_SESSION_EXPIRED;

    private final ObjectMapper objectMapper;

    private final StringRedisTemplate redisTemplate;

    public TokenServiceImpl(ObjectMapper objectMapper, StringRedisTemplate redisTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String generateAuthenticateToken(PersonInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        //使用UUD生成唯一的token
        String token = UUID.randomUUID().toString().replace("-", "");
        try {
            String json = objectMapper.writeValueAsString(userInfo);
            //存入redis中
            redisTemplate.opsForValue().set(REDIS_SESSION_KEY + ":" + token, json,Duration.ofSeconds(REDIS_SESSION_EXPIRED));
            return token;
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
        String personJson = redisTemplate.opsForValue().get(REDIS_SESSION_KEY + ":" + token);
        if (StringUtils.isEmpty(personJson)) {
            return null;
        }
        try {
            PersonInfo personInfo = objectMapper.readValue(personJson, PersonInfo.class);
            //获取成功后，重置一下该Token的过期时间
            redisTemplate.expire(REDIS_SESSION_KEY + ":" + token, Duration.ofSeconds(REDIS_SESSION_EXPIRED));
            return personInfo;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
