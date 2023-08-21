package check_in42.backend.allException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_ISSUED_TOKEN(1000, "파싱에 실패한 토큰입니다."),
    EXPIRED_TOKEN(1001, "유효기간이 만료된 토큰입니다."),
    ACCESS_TOKEN_NOT_FOUND(1002, "유효한 토큰이 없습니다"),
    REFRESH_TOKEN_NOT_FOUND_EXCEPTION(1003, "유효한 리프레시 토큰이 없습니다"),
    NO_USER(1004, "유저가 없습니다");
    private final int code;
    private final String message;
}
