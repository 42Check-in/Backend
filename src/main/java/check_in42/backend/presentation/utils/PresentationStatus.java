package check_in42.backend.presentation.utils;

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

    private static final Map<String, Integer> descriptionToOrdinal = new HashMap<>();

    static {
        for (PresentationStatus description : PresentationStatus.values()) {
            descriptionToOrdinal.put(description.description, description.ordinal());
        }
    }

    public static int getOrdinalByDescription(String description) {
        Integer ordinalValue = descriptionToOrdinal.get(description);
        if (ordinalValue != null) {
            return ordinalValue;
        }
        throw new IllegalArgumentException("Invalid description: " + description);
    }

}
