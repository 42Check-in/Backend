package check_in42.backend.equipments.utils;

import java.util.HashMap;
import java.util.Map;

public class EnumUtils {

    public static <T extends Enum<T>>Map<String, Integer> createDescriptionToOrdinalMap(Class<T> enumClass) {
        Map<String, Integer> descriptionToOrdinal = new HashMap<>();

        for (T enumConstant : enumClass.getEnumConstants()) {
            descriptionToOrdinal.put(enumConstant.name(), enumConstant.ordinal());
        }

        return descriptionToOrdinal;
    }

    public static int getOrdinalByDescription(Map<String, Integer> descriptionToOrdinal, String description) {
        Integer ordinalValue = descriptionToOrdinal.get(description);

        if (ordinalValue != null)
            return ordinalValue;
        return 0;
    }
}
