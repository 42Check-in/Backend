package check_in42.backend.allException;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CustomException extends RuntimeException{

    private final int code;
    private final String message;

    public CustomException(final ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
