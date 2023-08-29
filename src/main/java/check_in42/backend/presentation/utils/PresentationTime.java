package check_in42.backend.presentation.utils;

import check_in42.backend.equipments.utils.EnumUtils;
import lombok.Getter;

import java.util.HashMap;
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
    private static final Map<String, Integer> descriptionToOrdinal = new HashMap<>();

    static {
        for (PresentationTime description : PresentationTime.values()) {
            descriptionToOrdinal.put(description.description, description.ordinal());
        }
    }

    public static int getOrdinalByDescription(String description) {
        Integer ordinalValue = descriptionToOrdinal.get(description);
        if (ordinalValue != null) {
            return ordinalValue;
        }
        return 0;
    }
}
