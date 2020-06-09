package cn.pengan.service.impl;

import cn.pengan.dao.IAreaDao;
import cn.pengan.entity.Area;
import cn.pengan.service.IAreaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements IAreaService {
    private static final String AREA_KEY = "area_key";

    private final IAreaDao areaDao;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    public AreaServiceImpl(IAreaDao areaDao, StringRedisTemplate redisTemplate,ObjectMapper objectMapper) {
        this.areaDao = areaDao;
        this.redisTemplate = redisTemplate;
        this.objectMapper= objectMapper;
    }

    public boolean deleteAreaById(Integer id) {
        //删除数据库后的在删除缓存中的
        return redisTemplate.delete(AREA_KEY);
    }

    @Override
    public List<Area> findAll() {
        List<Area> areas = null;
        try {
            if (!redisTemplate.hasKey(AREA_KEY)) {
                areas = areaDao.findAll();
                String json = objectMapper.writeValueAsString(areas);
                redisTemplate.opsForValue().set(AREA_KEY, json);
            } else {
                String json = redisTemplate.opsForValue().get(AREA_KEY);
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Area.class);
                areas = objectMapper.readValue(json, javaType);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return areas;
    }
}
