package jpa.shoppingmall;

import jpa.shoppingmall.interceptor.AdminInterceptor;
import jpa.shoppingmall.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/login","/members/new", "/css/**", "/js/**");

        registry.addInterceptor(new AdminInterceptor())
                .order(2)
                .addPathPatterns("/members");
    }
}
