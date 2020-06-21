package cn.pengan.service;

import cn.pengan.entity.PersonInfo;

import javax.servlet.http.HttpServletRequest;

public interface IJwtService {

    String generateJwtToken(PersonInfo personInfo);

    PersonInfo getPersonInfoByJwtToken(String token);

    PersonInfo getPersonInfoByJwtToken(HttpServletRequest request);
}
