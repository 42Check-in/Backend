package check_in42.backend.user.exception;

import check_in42.backend.allException.CustomException;
import check_in42.backend.allException.ErrorCode;

public class IllegalRoleException extends CustomException {

    public IllegalRoleException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class NotStaffException extends IllegalRoleException {
        public NotStaffException() {
            super(ErrorCode.NOT_STAFF);
        }
    }

    public static class NotMemberException extends IllegalRoleException {
        public NotMemberException() {
            super(ErrorCode.NOT_MEMBER);
        }
    }
}
