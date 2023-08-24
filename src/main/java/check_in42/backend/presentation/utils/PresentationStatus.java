package check_in42.backend.presentation.utils;

import lombok.Getter;

@Getter
public enum PresentationStatus {
    NULL("취소"),
    PENDING("신청 중"),
    SCHEDULE_REGISTERED("스케줄 등록 완료"),
    AGENDA_REGISTERED("아젠다 등록 완료"),
    LECTURE_COMPLETED("강의 완료"),
    WAITING("대기 중");

    private final String description;

    PresentationStatus(String description) {
        this.description = description;
    }
}
