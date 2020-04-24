package cn.pengan.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            //response.getWriter().println(new Result(false,"用户未登录"));
            return false;
        }
        return true;
    }
}
