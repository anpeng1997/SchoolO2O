package cn.pengan.controller.superadmin.api;

import cn.pengan.dto.Result;
import cn.pengan.entity.LocalAuth;
import cn.pengan.entity.PersonInfo;
import cn.pengan.service.IJwtService;
import cn.pengan.service.ILocalAuthService;
import cn.pengan.service.ITokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api("登录API")
@RequestMapping("/api/login")
public class LoginApiController {

    private final ILocalAuthService localAuthService;
    private final ITokenService tokenService;
    private final IJwtService jwtService;

    public LoginApiController(ILocalAuthService localAuthService,
                              ITokenService tokenService,
                              IJwtService jwtService) {
        this.localAuthService = localAuthService;
        this.tokenService = tokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("")
    @ApiOperation("login")
    public Result login(@RequestBody LocalAuth user, HttpServletRequest servletRequest) {
        if (StringUtils.isEmpty(user.getUserName().trim()) || StringUtils.isEmpty(user.getPassword().trim())) {
            return new Result(false, "账号和密码不能为空");
        }
        //密码加密后在比对
        String md5Pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pwd);
        LocalAuth localAuth = localAuthService.findLocalAuth(user.getUserName(), user.getPassword());
        if (localAuth == null) {
            return new Result(false, "账号或密码错误");
        }
        PersonInfo personInfo = localAuth.getPersonInfo();
        if (personInfo.getAdminFlag() != 1) {
            return new Result(false, "当前登录用户不是管理员");
        }
        //String token = tokenService.generateAuthenticateToken(personInfo);
        String token = jwtService.generateJwtToken(personInfo);
        if (StringUtils.isEmpty(token)) {
            return new Result(false, "登录失败，获取token为null");
        }
        Result result = new Result();
        result.setSuccess(true);
        result.setData(token);
        return result;
    }
}
