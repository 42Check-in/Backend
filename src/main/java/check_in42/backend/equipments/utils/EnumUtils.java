package check_in42.backend.equipments.utils;

import java.util.HashMap;
import java.util.Map;

public class EnumUtils<T extends Enum<T>> {
    private final Map<String, Integer> descriptionToOrdinal = new HashMap<>();

    public EnumUtils(Class<T> enumClass) {
        for (T value : enumClass.getEnumConstants()) {
            descriptionToOrdinal.put(value.toString(), value.ordinal());
        }
    }

    public int getOrdinalByDescription(String description) {
        Integer ordinalValue = descriptionToOrdinal.get(description);
        if (ordinalValue != null) {
            return ordinalValue;
        }
        return 0;
    }
}
