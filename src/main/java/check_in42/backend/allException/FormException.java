package check_in42.backend.allException;

import check_in42.backend.allException.CustomException;
import check_in42.backend.allException.ErrorCode;

public class FormException extends CustomException {

    public FormException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class FormIdRunTimeException extends FormException {
        public FormIdRunTimeException() {
            super(ErrorCode.INVALIDED_FORMID);
        }
    }
}
