package check_in42.backend.auth.exception;

import check_in42.backend.allException.CustomException;
import check_in42.backend.allException.ErrorCode;

public class TokenException extends CustomException {

    public TokenException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class NotIssuedTokenException extends TokenException{
        public NotIssuedTokenException() {
            super(ErrorCode.NOT_ISSUED_TOKEN);
        }
    }

    public static class ExpiredAccessTokenException extends TokenException {
        public ExpiredAccessTokenException() {
            super(ErrorCode.EXPIRED_ACCESS_TOKEN);
        }
    }

    public static class ExpiredRefreshTokenException extends TokenException {
        public ExpiredRefreshTokenException() {
            super(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
    }
}
