package check_in42.backend.auth.interceptor;

import check_in42.backend.auth.exception.AuthorizationException;
import check_in42.backend.auth.jwt.TokenProvider;
import check_in42.backend.auth.utils.UserContext;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Order(value = 2)
@RequiredArgsConstructor
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final UserContext userContext;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //exception 추가해야함
        final String token = TokenHeaderValidate.extractToken(request)
                .orElseThrow(AuthorizationException.AccessTokenNotFoundException::new);
        final Claims claims = tokenProvider.parseAccessTokenClaim(token);
        final String intraId = claims.get("intraId", String.class);
        userService.findByName(intraId).orElseThrow(UserRunTimeException.NoUserException::new);

        userContext.setIntraId(intraId);
        log.info(intraId);
        //유저가 맴버인지 확인하는 거 추가해야함
        return true;
    }
}
