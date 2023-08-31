package check_in42.backend.user.exception;

import check_in42.backend.allException.CustomException;
import check_in42.backend.allException.ErrorCode;
import check_in42.backend.allException.FormException;

public class UserRunTimeException extends CustomException {

    public UserRunTimeException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class NoUserException extends UserRunTimeException {
        public NoUserException() {
            super(ErrorCode.NO_USER);
        }
    }

    public static class FormIdDoesNotExist extends UserRunTimeException {
        public FormIdDoesNotExist() {
            super(ErrorCode.INVALIDED_ID);
        }
    }
}
