package check_in42.backend.conferenceRoom.exception;

import check_in42.backend.allException.CustomException;
import check_in42.backend.allException.ErrorCode;

public class ConferenceException extends CustomException {

    public ConferenceException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static class ReservationRunTimeException extends ConferenceException {
        public ReservationRunTimeException() {
            super(ErrorCode.INVALIDED_RESERVATION);
        }
    }

    public static class DuplicateTimeException extends ConferenceException {
        public DuplicateTimeException() {
            super(ErrorCode.DUPLICATE_TIME);
        }
    }

    public static class AlreadyReserved extends ConferenceException {
        public AlreadyReserved() {
            super(ErrorCode.ALREADY_RESERVED);
        }
    }
}
