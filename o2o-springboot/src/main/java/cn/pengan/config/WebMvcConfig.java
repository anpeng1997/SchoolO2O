package cn.pengan.config;

import cn.pengan.interceptors.LoginInterceptor;
import cn.pengan.interceptors.ShopOperationInterceptors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadfile.mapping}")
    private String mapping;

    @Value("${uploadfile.path}")
    private String localPath;

    //拦截器加载的时间点在springcontext之前，所以在拦截器中注入才为null
    //所以我们应该使用@Bean提前去加载
    @Bean
    public LoginInterceptor addLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public ShopOperationInterceptors addShopOperationInterceptors() {
        return new ShopOperationInterceptors();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //上传文件映射目录
        registry.addResourceHandler("/" + mapping + "/**").addResourceLocations("file:" + localPath);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //TODO: 登录拦截器
        registry.addInterceptor(addLoginInterceptor()).addPathPatterns("/api/**")
                .excludePathPatterns("/api/login", "/api/frontdesk/**");
        registry.addInterceptor(addShopOperationInterceptors()).addPathPatterns("/api/shop/modify");
    }
}
