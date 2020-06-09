package cn.pengan.dao;

import cn.pengan.entity.PersonInfo;

public interface IPersonInfoDao {
    PersonInfo findPersonInfoById(Long id);
}
