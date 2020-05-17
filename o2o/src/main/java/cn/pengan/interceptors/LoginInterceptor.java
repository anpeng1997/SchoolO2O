package cn.pengan.interceptors;

import cn.pengan.dto.Result;
import cn.pengan.entity.PersonInfo;
import cn.pengan.service.ITokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //跨域请求时，会先询问一次（为OPTIONS请求）,所以我们要这个拦截器里判断是否为OPTIONS请求
        String method = request.getMethod();
        if (RequestMethod.OPTIONS.name().equals(method)) {
            return true;
        }
        PersonInfo person = tokenService.getPersonByToken(request);
        if (person == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            String jsonStr = objectMapper.writeValueAsString(new Result(false, "用户未登录"));
            response.getWriter().println(jsonStr);
            return false;
        }
        return true;
    }
}
