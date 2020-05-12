package cn.pengan.service;

public interface ITokenService {
    /**
     * 生成认证token
     * @param userInfo 需要保存的用户对象
     * @return
     */
    String generateAuthenticateToken(Object userInfo);
}
