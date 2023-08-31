package check_in42.backend.conferenceRoom.exception;

import check_in42.backend.allException.CustomException;
import check_in42.backend.allException.ErrorCode;

public class ConferenceException extends CustomException {

    public ConferenceException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static class reservationRunTimeException extends ConferenceException {

        public reservationRunTimeException() {
            super(ErrorCode.INVALIDED_ROOM);
        }
    }


}
