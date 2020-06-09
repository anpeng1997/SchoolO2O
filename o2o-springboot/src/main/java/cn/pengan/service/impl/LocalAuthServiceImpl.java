package cn.pengan.service.impl;

import cn.pengan.dao.ILocalAuthDao;
import cn.pengan.dto.LocalAuthExecution;
import cn.pengan.entity.LocalAuth;
import cn.pengan.enums.LocalAuthStatusEnum;
import cn.pengan.exceptions.LocalAuthException;
import cn.pengan.service.ILocalAuthService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
    public LocalAuthExecution insertLocalAuth(LocalAuth auth) throws LocalAuthException {
        LocalAuthExecution execution;
        try {
            String password = auth.getPassword();
            String mdsPwd = DigestUtils.md5DigestAsHex(password.getBytes());
            auth.setPassword(mdsPwd);
            int result = localAuthDao.insertLocalAuth(auth);
            if (result >= 1) {
                execution = new LocalAuthExecution(LocalAuthStatusEnum.SUCCESS);
            } else {
                execution = new LocalAuthExecution(LocalAuthStatusEnum.FAIL);
            }
        } catch (DuplicateKeyException duplicateKeyEx) {
            duplicateKeyEx.printStackTrace();
            throw new LocalAuthException(LocalAuthStatusEnum.DUPLICATE_KEY.getStateInfo());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new LocalAuthException(LocalAuthStatusEnum.INNER_ERROR.getStateInfo() + ex.getMessage());
        }
        return execution;
    }
}
