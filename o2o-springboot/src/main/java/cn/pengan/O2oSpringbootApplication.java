package cn.pengan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//扫描dao下所有的mapper(配置了之后，不需要在dao接口上单个的配置@Mapeer)
@MapperScan("cn.pengan.dao")
//开启事务管理
@EnableTransactionManagement
public class O2oSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(O2oSpringbootApplication.class, args);
    }

}
