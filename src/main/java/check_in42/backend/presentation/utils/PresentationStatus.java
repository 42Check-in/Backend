package check_in42.backend.presentation.utils;

import check_in42.backend.equipments.utils.EnumUtils;
import check_in42.backend.equipments.utils.EquipmentType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


@Getter
public enum PresentationStatus {
    PENDING("신청 중"),
    SCHEDULE_REGISTERED("스케줄 등록 완료"),
    AGENDA_REGISTERED("아젠다 등록 완료"),
    LECTURE_COMPLETED("강의 완료"),
    WAITING("대기 중");

    private String name;

    PresentationStatus(String name) {
        this.name = name;
    }
    public static int getOrdinalByDescription(String description) {
        return EnumUtils.getOrdinalByDescription(PresentationStatus.class, description);
    }

}
