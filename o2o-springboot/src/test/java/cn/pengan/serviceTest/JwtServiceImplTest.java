package cn.pengan.serviceTest;

import cn.pengan.entity.PersonInfo;
import cn.pengan.service.IJwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtServiceImplTest {

    @Autowired
    private IJwtService jwtService;

    @Test
    void generateJwtTest() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        personInfo.setName("test");
        String token = jwtService.generateJwtToken(personInfo);
        System.out.println(token);
    }

    @Test
    void getJwtTest() {
        PersonInfo personInfoByJwtToken = jwtService.getPersonInfoByJwtToken(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ0ZXN0IiwibmFtZSI6InRlc3" +
                        "QiLCJleHAiOjE1OTI3NDY0ODgsInVzZXJJZCI6MX0.4RpDT-8wrmFA_si_Qf4Uwou0TXh6A9yUpDG435y4-UM");
        System.out.println(personInfoByJwtToken);
    }
}
