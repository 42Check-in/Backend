package check_in42.backend.auth.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
public class TokenHeaderValidate {

    public static Optional<String> extractToken(final HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info(authorization);
        if (Strings.isEmpty(authorization)) {
            log.info("???");
            return Optional.empty();
        }
        return getToken(authorization.split(" "));
    }

    private static Optional<String> getToken(final String[] token) {
        log.info(token[0]);
        log.info(token[1]);
        if (token.length != 2 || !token[0].equals("Bearer ")) {
            log.info("tlqkf");
            return Optional.empty();
        }
        return Optional.ofNullable(token[1]);
    }
}
