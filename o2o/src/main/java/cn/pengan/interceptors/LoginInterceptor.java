package cn.pengan.interceptors;

import cn.pengan.entity.PersonInfo;
import cn.pengan.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ITokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //跨域请求时，会先询问一次（为OPTIONS请求）,所以我们要这个拦截器里判断一次
        //TODO: 测试一下前端自己发送options是否能通过
        String method = request.getMethod();
        if (RequestMethod.OPTIONS.name().equals(method)){
            return true;
        }
        String token = request.getHeader("Authenticate-Token");
        PersonInfo person = tokenService.getPersonByToken(token);
        if (person == null) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            //response.getWriter().println(new Result(false,"用户未登录"));
            return false;
        }
        return true;
    }
}
