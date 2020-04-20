package cn.pengan.controller.superadmin.api;

import cn.pengan.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/headline")
@Api(value = "首页轮播图API")
public class HeadLineApiController {

    @GetMapping("/get")
    @ApiOperation("获得轮播图信息")
    public Result get() {
        return new Result();
    }
}

