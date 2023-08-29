package check_in42.backend.presentation.utils;

import check_in42.backend.equipments.utils.EnumUtils;
import lombok.Getter;


@Getter
public enum PresentationStatus {
    PENDING("신청 중"),
    SCHEDULE_REGISTERED("스케줄 등록 완료"),
    AGENDA_REGISTERED("아젠다 등록 완료"),
    LECTURE_COMPLETED("강의 완료"),
    WAITING("대기 중");

    private String description;

    PresentationStatus(String description) {
        this.description = description;
    }
    private static final EnumUtils<PresentationStatus> descriptionMapper =
            new EnumUtils<>(PresentationStatus.class);

    public static int getOrdinalByDescription(String description) {
        return descriptionMapper.getOrdinalByDescription(description);
    }

}
