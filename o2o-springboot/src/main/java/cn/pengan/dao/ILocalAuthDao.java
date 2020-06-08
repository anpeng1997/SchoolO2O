package cn.pengan.dao;

import cn.pengan.entity.LocalAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ILocalAuthDao {
    LocalAuth findLocalAuth(@Param("username") String name, @Param("password") String pwd);

    int insertLocalAuth(LocalAuth auth);
}
