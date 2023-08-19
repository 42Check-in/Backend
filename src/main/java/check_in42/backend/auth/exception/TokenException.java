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

    public static class ExpiredTokenException extends TokenException {
        public ExpiredTokenException() {
            super(ErrorCode.EXPIRED_TOKEN);
        }
    }
}
