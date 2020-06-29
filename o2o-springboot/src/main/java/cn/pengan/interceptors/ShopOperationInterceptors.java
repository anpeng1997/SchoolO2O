package cn.pengan.interceptors;

import cn.pengan.dto.Result;
import cn.pengan.entity.PersonInfo;
import cn.pengan.entity.Shop;
import cn.pengan.service.IJwtService;
import cn.pengan.service.IShopService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.StringConverter;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShopOperationInterceptors extends HandlerInterceptorAdapter {

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private IShopService shopService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PersonInfo personInfo = jwtService.getPersonInfoByJwtToken(request);
        if (personInfo != null) {
            String shopInfoStr = request.getParameter("shopInfo");
            Shop shopInfo = objectMapper.readValue(shopInfoStr,Shop.class);
            Shop shop = shopService.findShopById(shopInfo.getShopId());
            if (shop != null) {
                if (shop.getOwnerId() == personInfo.getUserId()) {
                    return true;
                }
            }
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        String jsonStr = objectMapper.writeValueAsString(new Result(false, "没有操作该店铺的权限"));
        response.getWriter().println(jsonStr);
        return false;
    }
}
