package check_in42.backend.auth.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class TokenHeaderValidate {

    public static Optional<String> extractToken(final HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isEmpty(authorization)) {
            return Optional.empty();
        }
        return getToken(authorization.split(" "));
    }

    private static Optional<String> getToken(final String[] token) {
        if (token.length != 2 || !token[0].equals("Bearer ")) {
            return Optional.empty();
        }
        return Optional.ofNullable(token[1]);
    }
}
