package cn.pengan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
public class O2oSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(O2oSpringbootApplication.class, args);
    }

}
