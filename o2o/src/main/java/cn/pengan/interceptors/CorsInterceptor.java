package cn.pengan.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        /**
         * Access-Control-Allow-Credentials 允许发送cookie，默认情况下Cookie不包括在Cors请求之中
         * 需要注意的是，如果要发送Cookie，
         * Access-Control-Allow-Origin就不能设为星号，
         * 必须指定明确的、与请求网页一致的域名。
         * 同时，Cookie依然遵循同源政策，只有用服务器域名设置的Cookie才会上传，
         * 其他域名的Cookie并不会上传，且（跨源）原网页代码中的document.cookie也无法读取服务器域名下的Cookie。
         */
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String origin = request.getHeader("Origin");
        //必须指定明确的、与请求网页一致的域名。
        response.setHeader("Access-Control-Allow-Origin", origin);

        //Access-Control-Allow-Headers ： 设置允许那些报文头跨域(不能用*通配符)
        //x-requested-with是代表在请求头包含时必须响应
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        return true;
    }
}
