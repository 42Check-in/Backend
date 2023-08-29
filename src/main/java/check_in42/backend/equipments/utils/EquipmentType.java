package check_in42.backend.equipments.utils;

import check_in42.backend.presentation.utils.PresentationTime;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

//db엔 string, front에는 number
@Getter
public enum EquipmentType {
    ETC("그 외"),
    MACBOOK("맥북"),
    NOTEBOOK("삼성 노트북"),
    IPAD("아이패드");

    private final String name;
    EquipmentType(String name) {
        this.name = name;
    }

    private static final Map<String, Integer> descriptionToOrdinal = new HashMap<>();

    static {
        for (EquipmentType description : EquipmentType.values()) {
            descriptionToOrdinal.put(description.name, description.ordinal());
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
