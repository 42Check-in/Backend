package check_in42.backend.presentation.utils;

import check_in42.backend.equipments.utils.EnumUtils;
import lombok.Getter;

import java.util.Map;

@Getter
public enum PresentationTime {
    ZERO("15분"),
    ONE("30분"),
    TWO("45분"),
    THREE("1시간"),
    FOUR("1시간 이상");

    private final String description;
    PresentationTime(String description) {
        this.description = description;
    }
    private static final Map<String, Integer> descriptionToOrdinal = EnumUtils.createDescriptionToOrdinalMap(PresentationTime.class);


    public static int getOrdinalByDescription(String description) {
        return EnumUtils.getOrdinalByDescription(descriptionToOrdinal, description);
    }
}
