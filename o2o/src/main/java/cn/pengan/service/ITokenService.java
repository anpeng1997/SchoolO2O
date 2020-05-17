package cn.pengan.service;

import cn.pengan.entity.PersonInfo;

import javax.servlet.http.HttpServletRequest;

public interface ITokenService {
    /**
     * 生成认证token
     *
     * @param userInfo 需要保存的用户对象
     * @return
     */
    String generateAuthenticateToken(PersonInfo userInfo);

    /**
     * 根据request Header token获取redis中的personInfo
     * @param request
     * @return 当不存在时，返回null
     */
    PersonInfo getPersonByToken(HttpServletRequest request);
}
