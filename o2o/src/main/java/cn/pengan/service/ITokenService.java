package cn.pengan.service;

import cn.pengan.entity.PersonInfo;

public interface ITokenService {
    /**
     * 生成认证token
     *
     * @param userInfo 需要保存的用户对象
     * @return
     */
    String generateAuthenticateToken(Object userInfo);

    /**
     * 根据token获取redis中的personInfo
     * @param token
     * @return 当不存在时，返回null
     */
    PersonInfo getPersonByToken(String token);
}
