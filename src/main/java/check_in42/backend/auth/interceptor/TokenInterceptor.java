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
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final UserContext userContext;

    public boolean isStaffClaimPresent(Claims claims) {
        return claims.containsKey("staff");
    }

    public boolean getStaffClaimValue(Claims claims) {
        Object staffClaimValue = claims.get("staff");
        if (staffClaimValue instanceof Boolean) {
            return (Boolean) staffClaimValue;
        }
        return false;
    }
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        //exception 추가해야함
        final String token = TokenHeaderValidate.extractToken(request)
                .orElseThrow(AuthorizationException.AccessTokenNotFoundException::new);
        final Claims claims = tokenProvider.parseAccessTokenClaim(token);
        final String intraId = claims.get("intraId", String.class);

        userService.findByName(intraId).orElseThrow(UserRunTimeException.NoUserException::new);
        userContext.setIntraId(intraId);
        log.info("In tokenInterceptor! intra id is...." + intraId);
        //유저가 맴버인지 확인하는 거 추가해야함

        boolean isStaffClaimPresent = isStaffClaimPresent(claims);

        if (isStaffClaimPresent) {
            boolean isStaff = getStaffClaimValue(claims);
            userContext.setStaff(isStaff);
            log.info("key is present!!!!!!!" + isStaff);
        } else  {
            log.info("key doesn't exist;;;;;");
            userContext.setStaff(false);
        }
        return true;
    }
}
