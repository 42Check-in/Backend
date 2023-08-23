package check_in42.backend.auth.config;

import check_in42.backend.auth.argumentresolver.AuthArgumentResolver;
import check_in42.backend.auth.interceptor.LoginInterceptor;
import check_in42.backend.auth.interceptor.TokenInterceptor;
import check_in42.backend.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final TokenInterceptor tokenInterceptor;
    private final AuthArgumentResolver authArgumentResolver;
//
//    private static final List<String> LOGIN_URL = List.of("/oauth/login");
//    private static final List<String> TOKEN_URL = List.of("/reissue", "/conference-room/*",
//            "/presentation/*", "/visitors/*", "/equipments/*", "/vocal/*");
//    @Override
//    public void addInterceptors(final InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns(LOGIN_URL);
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns(TOKEN_URL);
//    }
//
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }
}
