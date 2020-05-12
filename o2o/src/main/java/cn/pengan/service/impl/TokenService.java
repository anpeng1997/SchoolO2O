package cn.pengan.service.impl;

import cn.pengan.service.ITokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService implements ITokenService {

    @Value("${redis.user.session.key}")
    private String REDIS_SESSION_KEY;

    private final ObjectMapper objectMapper;

    public TokenService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String generateAuthenticateToken(Object userInfo) {
        if (userInfo == null) {
            return null;
        }
        //使用UUD生成唯一的token
        String token = UUID.randomUUID().toString().replace("-", "");
        //TODO: objectMapper.writeValueAsString()
        return token;
    }
}
