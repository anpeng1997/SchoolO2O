package cn.pengan.controller.superadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/superadmin/productcategory")
public class ProductCategoryController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String ProductCategoryList() {
        return "/product/productcategory";
    }
}
