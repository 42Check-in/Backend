package check_in42.backend.allException;

import check_in42.backend.auth.exception.TokenException;
import check_in42.backend.user.exception.UserRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AllExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity handleAuthenticationException(final CustomException e) {
        log.info(e.toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getCode());
    }
    @ExceptionHandler(TokenException.class)
    private ResponseEntity handleTokenException(final CustomException e) {
        log.info(e.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
    }

    @ExceptionHandler(UserRunTimeException.class)
    private ResponseEntity handleUserRunException(final CustomException e) {
        log.info(e.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCode());
    }
}
