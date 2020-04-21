package cn.pengan.service;

import cn.pengan.dto.LocalAuthExecution;
import cn.pengan.entity.LocalAuth;

public interface ILocalAuthService {
    LocalAuth findLocalAuth(String name,String pwd);
    LocalAuthExecution insertLocalAuth(LocalAuth auth);
}
