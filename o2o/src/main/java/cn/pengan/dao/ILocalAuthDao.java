package cn.pengan.dao;

import cn.pengan.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

public interface ILocalAuthDao {
    LocalAuth findLocalAuth(@Param("name") String name,@Param("pwd") String pwd);

    int insertLocalAuth(LocalAuth auth);
}
