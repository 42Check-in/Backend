package check_in42.backend.presentation.utils;

import check_in42.backend.equipments.utils.EnumUtils;
import check_in42.backend.equipments.utils.EquipmentType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum PresentationStatus {
    NULL("취소"),
    PENDING("신청 중"),
    SCHEDULE_REGISTERED("스케줄 등록 완료"),
    AGENDA_REGISTERED("아젠다 등록 완료"),
    LECTURE_COMPLETED("강의 완료"),
    WAITING("대기 중");

    private String description;

    PresentationStatus(String description) {
        this.description = description;
    }

    private static final Map<String, Integer> descriptionToOrdinal = EnumUtils.createDescriptionToOrdinalMap(PresentationStatus.class);


    public static int getOrdinalByDescription(String description) {
        return EnumUtils.getOrdinalByDescription(descriptionToOrdinal, description);
    }

}
