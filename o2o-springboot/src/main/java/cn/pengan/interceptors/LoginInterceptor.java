package cn.pengan.interceptors;

import cn.pengan.dto.Result;
import cn.pengan.entity.PersonInfo;
import cn.pengan.service.IJwtService;
import cn.pengan.service.ITokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

//    @Autowired
//    private ITokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IJwtService jwtService;

    private final String HEADER_TOKEN_KEY = "Authenticate-Token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PersonInfo personInfo = jwtService.getPersonInfoByJwtToken(request);
        if (personInfo != null) {
            return true;
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        String jsonStr = objectMapper.writeValueAsString(new Result(false, "用户未登录"));
        response.getWriter().println(jsonStr);
        return false;
    }
}
