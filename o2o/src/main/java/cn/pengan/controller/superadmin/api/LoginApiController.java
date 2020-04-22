package cn.pengan.controller.superadmin.api;

import cn.pengan.dto.Result;
import cn.pengan.entity.LocalAuth;
import cn.pengan.entity.PersonInfo;
import cn.pengan.service.ILocalAuthService;
import io.swagger.annotations.Api;
import org.apache.ibatis.executor.ReuseExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api("登录API")
@RequestMapping("/api/login")
public class LoginApiController {

    private final ILocalAuthService localAuthService;

    public LoginApiController(ILocalAuthService localAuthService) {
        this.localAuthService = localAuthService;
    }

    @PostMapping("/")
    public Result login(String username, String password, HttpServletRequest servletRequest) {
        if (username != null && password != password) {
            return new Result(false, "账号和密码不能为空");
        }
        LocalAuth localAuth = localAuthService.findLocalAuth(username, password);
        if (localAuth == null) {
            return new Result(false, "账号或密码错误");
        }
        PersonInfo personInfo = localAuth.getPersonInfo();
        if (personInfo.getAdminFlag() != 1) {
            return new Result(false,"当前登录用户不是管理员");
        }
        servletRequest.getSession().setAttribute("user",personInfo);
        return new Result(true,"登录成功");
    }
}
