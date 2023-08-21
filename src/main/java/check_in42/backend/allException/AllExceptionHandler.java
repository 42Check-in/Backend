package check_in42.backend.allException;

import check_in42.backend.auth.exception.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AllExceptionHandler {

    @ExceptionHandler(TokenException.class)
    private ResponseEntity handleTokenException(final CustomException e) {
        log.info(e.toString());
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }
}
