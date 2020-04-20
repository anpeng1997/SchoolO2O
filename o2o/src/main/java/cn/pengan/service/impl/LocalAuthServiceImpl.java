package cn.pengan.service.impl;

import cn.pengan.dao.ILocalAuthDao;
import cn.pengan.entity.LocalAuth;
import cn.pengan.service.ILocalAuthService;
import org.springframework.stereotype.Service;

@Service
public class LocalAuthServiceImpl implements ILocalAuthService {

    private final ILocalAuthDao localAuthDao;

    public LocalAuthServiceImpl(ILocalAuthDao localAuthDao) {
        this.localAuthDao = localAuthDao;
    }

    @Override

    public LocalAuth findLocalAuth(String name, String pwd) {
        LocalAuth localAuth = localAuthDao.findLocalAuth(name, pwd);
        return localAuth;
    }

    @Override
    public int insertLocalAuth(LocalAuth auth) {
        return localAuthDao.insertLocalAuth(auth);
    }
}
