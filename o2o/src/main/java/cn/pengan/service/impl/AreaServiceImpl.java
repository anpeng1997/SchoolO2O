package cn.pengan.service.impl;

import cn.pengan.annotations.DataOperationLog;
import cn.pengan.cache.JedisUtil;
import cn.pengan.dao.IAreaDao;
import cn.pengan.entity.Area;
import cn.pengan.service.IAreaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaServiceImpl implements IAreaService {
    private static final String AREA_KEY = "area_key";

    private final IAreaDao areaDao;
    private final JedisUtil.Keys jedisKeys;
    private final JedisUtil.Strings jedisStrings;
    private final ObjectMapper objectMapper;

    public AreaServiceImpl(IAreaDao areaDao,
                           JedisUtil.Keys jedisKeys,
                           JedisUtil.Strings jedisStrings,
                           ObjectMapper objectMapper) {
        this.areaDao = areaDao;
        this.jedisKeys = jedisKeys;
        this.jedisStrings = jedisStrings;
        this.objectMapper = objectMapper;
    }

    public int deleteAreaById(Integer id) {
        //删除数据库后的在删除缓存中的
        jedisKeys.deleteKey(AREA_KEY);
        return 1;
    }

    @DataOperationLog("查询所有的area")
    @Override
    public List<Area> findAll() {
        List<Area> areas = null;
        try {
            if (!jedisKeys.KeyExist(AREA_KEY)) {
                areas = areaDao.findAll();
                String json = objectMapper.writeValueAsString(areas);
                jedisStrings.set(AREA_KEY, json);
            } else {
                String json = jedisStrings.get(AREA_KEY);
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Area.class);
                areas = objectMapper.readValue(json, javaType);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return areas;
    }
}
