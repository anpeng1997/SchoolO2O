package cn.pengan.service.impl;

import cn.pengan.entity.PersonInfo;
import cn.pengan.service.IJwtService;
import com.alibaba.druid.sql.visitor.functions.If;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements IJwtService {


    @Value("${jwt.encryption-key}")
    private String ENCRYPTION_KEY;

    @Value("${jwt.expired}")
    private int EXPIRED_TIME;


    private final String HEADER_TOKEN_KEY = "Authenticate-Token";

    @Override
    public String generateJwtToken(PersonInfo personInfo) {
        if (personInfo == null) {
            return null;
        }
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRED_TIME * 1000))
                .withAudience(personInfo.getName())
                .withClaim("name", personInfo.getName())
                .withClaim("userId", personInfo.getUserId())
                .sign(Algorithm.HMAC256(ENCRYPTION_KEY));
    }

    @Override
    public PersonInfo getPersonInfoByJwtToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC256(ENCRYPTION_KEY))
                    .build()
                    .verify(token);
            Claim name = verify.getClaim("name");
            Claim userId = verify.getClaim("userId");
            PersonInfo personInfo = null;
            if (!name.isNull() && !userId.isNull()) {
                personInfo = new PersonInfo();
                personInfo.setName(name.asString());
                personInfo.setUserId(userId.asLong());
            }
            return personInfo;
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public PersonInfo getPersonInfoByJwtToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_TOKEN_KEY);
        return getPersonInfoByJwtToken(token);
    }
}
