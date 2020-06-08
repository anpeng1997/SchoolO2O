package cn.pengan.dao;

import cn.pengan.entity.PersonInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPersonInfoDao {
    PersonInfo findPersonInfoById(Long id);
}
