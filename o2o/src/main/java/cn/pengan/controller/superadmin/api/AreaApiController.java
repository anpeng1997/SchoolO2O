package cn.pengan.controller.superadmin.api;

import cn.pengan.dto.Result;
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
@RequestMapping("/api/areas")
public class AreaApiController {

    private static final Logger logger = LoggerFactory.getLogger(AreaApiController.class);

    private final IAreaService areaService;

    public AreaApiController(IAreaService areaService) {
        this.areaService = areaService;
    }

    @ApiOperation(value = "获取所有的区域列表信息", httpMethod = "GET")
    @RequestMapping(path = "", method = {RequestMethod.GET})
    public Result getAreaList(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        try {
            List<Area> areas = areaService.findAll();
            data.put("rows", areas);
            data.put("total", areas.size());
            return new Result(true, data);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }
}
