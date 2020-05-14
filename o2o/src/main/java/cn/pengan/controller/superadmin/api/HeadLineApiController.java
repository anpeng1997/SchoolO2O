package cn.pengan.controller.superadmin.api;

import cn.pengan.dto.Result;
import cn.pengan.entity.HeadLine;
import cn.pengan.service.IHeadLineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/headline")
@Api(value = "首页轮播图API")
public class HeadLineApiController {

    private final IHeadLineService headLineService;
    private final ObjectMapper objectMapper;

    public HeadLineApiController(IHeadLineService headLineService, ObjectMapper objectMapper) {
        this.headLineService = headLineService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/{size}")
    @ApiOperation("获得轮播图信息")
    public Result get(@PathVariable("size") Integer size) {
        List<HeadLine> headLineList = headLineService.findHeadLineList(size);
        return new Result(true, headLineList);
    }

    @PostMapping("")
    @ApiOperation("添加一个轮播图")
    public Result add(String headLine, MultipartFile imgFile) {
        if (StringUtils.isEmpty(headLine) || imgFile == null){
            return new Result(false, "提交数据不完整");
        }
        try {
            HeadLine headLineObj = objectMapper.readValue(headLine, HeadLine.class);
            if (StringUtils.isEmpty(headLineObj.getLineLink()) || StringUtils.isEmpty(headLineObj.getLineName())) {
                return new Result(false, "提交数据不完整");
            }
            int i = headLineService.insertHeadLine(headLineObj,imgFile.getInputStream(),imgFile.getOriginalFilename());
            if (i <= 0) {
                return new Result(false, "添加失败");
            }
            return new Result(true, headLine);
        } catch (JsonProcessingException e) {
            return new Result(false, "提交数据错误");
        } catch (IOException e) {
            return new Result(false, "提交图片出现错误");
        }

    }
}

