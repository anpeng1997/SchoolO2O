package cn.pengan.service;

import cn.pengan.entity.LocalAuth;

public interface ILocalAuthService {
    LocalAuth findLocalAuth(String name,String pwd);
    int insertLocalAuth(LocalAuth auth);
}
