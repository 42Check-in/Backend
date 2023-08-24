package check_in42.backend.auth.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private final TokenInterceptor tokenInterceptor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
//        if (request.getMethod().equals("OPTIONS")) {
//            return true;
//        }
        if (TokenHeaderValidate.extractToken(request).isEmpty()) {
            log.info("비어있어야 하자나");
            return true;
        }
        log.info("헤더가 비어있지 않는다");
        return tokenInterceptor.preHandle(request, response, handler);
    }
}
