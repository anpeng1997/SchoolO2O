package cn.pengan.service.impl;

import cn.pengan.dao.IHeadLineDao;
import cn.pengan.entity.HeadLine;
import cn.pengan.service.IHeadLineService;
import cn.pengan.util.ImageUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("headLineService")
public class HeadLineServiceImpl implements IHeadLineService {

    private final static String HEAD_LINE_KEY = "headLine_key";

    private final IHeadLineDao headLineDao;
   private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public HeadLineServiceImpl(IHeadLineDao headLineDao, StringRedisTemplate redisTemplate,
                               ObjectMapper objectMapper) {
        this.headLineDao = headLineDao;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public int insertHeadLine(HeadLine headLine, InputStream inputStream, String fileName) throws IOException {
        //初始化数据
        headLine.setCreateTime(new Date());
        headLine.setLastEditTime(new Date());
        headLine.setEnableStatus(1);
        int var1 = headLineDao.batchInsertHeadLine(Arrays.asList(headLine));
        if (var1 <= 0) {
            return var1;
        }
        //保存图片至本地
        String relativePath = ImageUtil.saveHeadLineImg(headLine.getLineId(), inputStream, fileName);
        headLine.setLineImg(relativePath);
        //更新图片
        int i = headLineDao.updateHeadLine(headLine);
        //删除缓存
        if (i > 0) {
            redisTemplate.delete(HEAD_LINE_KEY);
        }
        return i;
    }

    @Override
    public List<HeadLine> findHeadLineList(HeadLine headLineCondition) {
        List<HeadLine> headLines = null;
        //当EnableStatus为1时，说明是前台主页调用数据，所有要从缓存中读取数据
        Integer enableStatus = headLineCondition.getEnableStatus();
        if (enableStatus != null && enableStatus == 1) {
            try {
                if (!redisTemplate.hasKey(HEAD_LINE_KEY)) {
                    headLines = headLineDao.findHeadLineList(headLineCondition);
                    redisTemplate.opsForValue().set(HEAD_LINE_KEY, objectMapper.writeValueAsString(headLines));
                } else {
                    String json = redisTemplate.opsForValue().get(HEAD_LINE_KEY);
                    JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, HeadLine.class);
                    headLines = objectMapper.readValue(json, javaType);
                }
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else { //代表着后台来获取数据，这时候要直接去数据库中查询所有的数据返回
            headLines = headLineDao.findHeadLineList(headLineCondition);
        }
        return headLines;
    }

    @Override
    public int deleteHeadLineById(Long lineId) {
        int i = headLineDao.deleteHeadLineById(lineId);
        if (i > 0) {
            redisTemplate.delete(HEAD_LINE_KEY);
        }
        return i;
    }
}
