package check_in42.backend.auth.argumentresolver;

import check_in42.backend.auth.interceptor.TokenHeaderValidate;
import check_in42.backend.auth.jwt.TokenProvider;
import check_in42.backend.user.exception.UserRunTimeException;
import io.jsonwebtoken.Claims;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
        Optional<String> jwtToken = TokenHeaderValidate.extractToken(request);
        Claims claims = tokenProvider.parseClaim(jwtToken.get());

        return UserInfo.builder().intraId(claims.get("intraId", String.class));
    }
}
