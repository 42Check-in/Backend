package check_in42.backend.auth.config;

import check_in42.backend.auth.interceptor.LoginInterceptor;
import check_in42.backend.auth.interceptor.TokenInterceptor;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class AuthConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/*")
                .excludePathPatterns("/login");
        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/*")
                .excludePathPatterns("/login");
    }
}
