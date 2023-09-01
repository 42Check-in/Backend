package check_in42.backend.allException;

import check_in42.backend.auth.exception.AuthorizationException;
import check_in42.backend.auth.exception.TokenException;
import check_in42.backend.conferenceRoom.exception.ConferenceException;
import check_in42.backend.user.exception.IllegalRoleException;
import check_in42.backend.user.exception.UserRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AllExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity handleAuthenticationException(final CustomException e) {
        log.info(e.toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getCode());
    }
    @ExceptionHandler(TokenException.class)
    public ResponseEntity handleTokenException(final CustomException e) {
        log.info(e.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
    }

    @ExceptionHandler(UserRunTimeException.class)
    public ResponseEntity handleUserRunException(final CustomException e) {
        log.info(e.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCode());
    }

    @ExceptionHandler(ConferenceException.class)
    public ResponseEntity handleConferenceException(final CustomException e) {
        log.info(e.toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getCode());
    }

    @ExceptionHandler(FormException.class)
    public ResponseEntity handleFormException(final CustomException e) {
        log.info(e.toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getCode());
    }

    @ExceptionHandler(IllegalRoleException.class)
    public ResponseEntity handelRoleException(final CustomException e) {
        log.info(e.toString());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getCode());
    }
}
