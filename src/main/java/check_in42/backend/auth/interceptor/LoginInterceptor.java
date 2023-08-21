package check_in42.backend.auth.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.info("들어오면 안되는데 왜 들어옴");
        if (TokenHeaderValidate.extractToken(request).isEmpty()) {
            return true;
        }
        return tokenInterceptor.preHandle(request, response, handler);
    }
}
