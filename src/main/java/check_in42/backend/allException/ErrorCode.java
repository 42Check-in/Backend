package check_in42.backend.allException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //tokenException
    NOT_ISSUED_TOKEN(1000, "파싱에 실패한 토큰입니다."),
    EXPIRED_ACCESS_TOKEN(1001, "유효기간이 만료된 AccessToken입니다."),
    EXPIRED_REFRESH_TOKEN(1002, "유효기간이 만료된 RefreshToken입니다."),

    //authorizationException
    ACCESS_TOKEN_NOT_FOUND(1003, "유효한 토큰이 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(1004, "유효한 리프레시 토큰이 없습니다"),

    //userRunTimeException
    NO_USER(1005, "유저가 없습니다"),
    BAD_DATE(1006, "날짜가 양식에 맞지 않습니다"),

    //conferenceException
    INVALIDED_FORMID(1007, "클러스터 정보가 유효하지 않습니다"),

    //formException
    INVALIDED_ROOM(1008, "Form Id가 유효하지 않습니다");

    private final int code;
    private final String message;
}
