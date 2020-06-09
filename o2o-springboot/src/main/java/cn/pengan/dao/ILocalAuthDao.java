package cn.pengan.dao;

import cn.pengan.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocalAuthDao {
    LocalAuth findLocalAuth(@Param("username") String name, @Param("password") String pwd);

    int insertLocalAuth(LocalAuth auth);
}
