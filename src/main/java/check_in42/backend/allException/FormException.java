package check_in42.backend.allException;

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
