package check_in42.backend.auth.exception;

import check_in42.backend.allException.CustomException;
import check_in42.backend.allException.ErrorCode;

public class AuthorizationException extends CustomException {

    public AuthorizationException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class RefreshTokenNotFoundException extends AuthorizationException {
        public RefreshTokenNotFoundException() {
            super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }
    }

    public static class AccessTokenNotFoundException extends AuthorizationException {
        public AccessTokenNotFoundException() {
            super(ErrorCode.ACCESS_TOKEN_NOT_FOUND);
        }
    }
}
