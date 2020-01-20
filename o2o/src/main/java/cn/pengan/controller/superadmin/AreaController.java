package cn.pengan.controller.superadmin;

import cn.pengan.entity.Area;
import cn.pengan.service.IAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/superadmin")
public class AreaController {
    @Autowired
    private IAreaService areaService;

    Logger logger = LoggerFactory.getLogger(AreaController.class);

    @RequestMapping(path = "/arealist", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getAreaList() {
        logger.info("start....");
        long startTime = System.currentTimeMillis();
        Map<String, Object> model = new HashMap<>();
        try {
            List<Area> areas = areaService.findAll();
            model.put("rows", areas);
            model.put("total", areas.size());
            model.put("success", true);
        } catch (Exception e) {
            model.put("success", false);
            model.put("errormsg", e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}]毫秒",endTime-startTime);
        logger.info("end......");
        return model;
    }
}
