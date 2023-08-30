package check_in42.backend.presentation.utils;

import check_in42.backend.equipments.utils.EnumUtils;
import check_in42.backend.equipments.utils.EquipmentType;
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

    private final String name;
    PresentationTime(String description) {
        this.name = description;
    }
    public static int getOrdinalByDescription(String description) {
        return EnumUtils.getOrdinalByDescription(PresentationTime.class, description);
    }
}
