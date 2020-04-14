package cn.pengan.controller.superadmin.api;

import cn.pengan.entity.Area;
import cn.pengan.service.IAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(value = "区域API")
@RequestMapping("/api/area")
public class AreaApiController {

    private static final Logger logger = LoggerFactory.getLogger(AreaApiController.class);

    private final IAreaService areaService;

    public AreaApiController(IAreaService areaService) {
        this.areaService = areaService;
    }

    @ApiOperation(value = "获取所有的区域列表信息", httpMethod = "GET")
    @RequestMapping(path = "/getlist", method = {RequestMethod.GET})
    public Map<String, Object> getAreaList(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        try {
            List<Area> areas = areaService.findAll();
            model.put("rows", areas);
            model.put("total", areas.size());
            model.put("success", true);
        } catch (Exception e) {
            model.put("success", false);
            model.put("errorMsg", e.getMessage());
        }
        logger.info("logger info test..................");
        return model;
    }
}
