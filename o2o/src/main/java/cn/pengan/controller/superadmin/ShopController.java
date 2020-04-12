package cn.pengan.controller.superadmin;

import cn.pengan.entity.Shop;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(path = "/superadmin/shop")
public class ShopController {

    @RequestMapping(value = "/shopoperation", method = RequestMethod.GET)
    public String shopOperation() {
        return "/shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    public String shopList() {
        return "/shop/shoplist";
    }

    @RequestMapping(value = "/shopmanage", method = RequestMethod.GET)
    public String shopManage(Long id, HttpServletRequest request) {
        if (id == null && id <= 0) {
            return "/shop/shoplist";
        }
        Shop shop = new Shop();
        shop.setShopId(id);
        request.getSession().setAttribute("currentShop", shop);
        return "/shop/shopmanage";
    }
}
