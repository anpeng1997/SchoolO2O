package cn.pengan.dao;

import cn.pengan.entity.PersonInfo;

/**
 * Created by Enzo Cotter on 2020/4/20.
 */
public interface IPersonInfoDao {
    PersonInfo findPersonInfoById(Long id);
}
