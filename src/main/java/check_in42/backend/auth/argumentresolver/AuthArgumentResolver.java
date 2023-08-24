package check_in42.backend.auth.argumentresolver;

import check_in42.backend.auth.exception.AuthorizationException;
import check_in42.backend.auth.interceptor.TokenHeaderValidate;
import check_in42.backend.auth.jwt.TokenProvider;
import check_in42.backend.auth.utils.UserContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;
    private final UserContext userContext;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
        Optional<String> jwtToken = TokenHeaderValidate.extractToken(request);

        final String accessToken = jwtToken.orElseThrow(AuthorizationException.AccessTokenNotFoundException::new);
        final Claims claims = tokenProvider.parseAccessTokenClaim(accessToken);
        final String intraId = userContext.getIntraId();
        log.info(intraId + " 10 이거 잘 들어감?");
        return UserInfo.builder().intraId(claims.get("intraId", String.class)).build();
    }
}
