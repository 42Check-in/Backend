package check_in42.backend.equipments.utils;

import java.util.HashMap;
import java.util.Map;

public class EnumUtils {

    public static int getOrdinalByDescription(Class<? extends Enum<?>> enumValue, String description) {
        Map<String, Integer> descriptionToOrdinal = new HashMap<>();

        for (Enum<?> enumConstant : getEnumConstants(enumValue)) {
            descriptionToOrdinal.put(getEnumDescription(enumConstant), enumConstant.ordinal());
        }

        Integer ordinalValue = descriptionToOrdinal.get(description);
        if (ordinalValue != null) {
            return ordinalValue;
        }
        return 0; // Return a value indicating that the description was not found
    }

    private static Enum<?>[] getEnumConstants(Class<? extends Enum<?>> enumClass) {
        return enumClass.getEnumConstants();
    }

    private static String getEnumDescription(Enum<?> enumConstant) {
        try {
            return (String) enumConstant.getClass().getMethod("getName").invoke(enumConstant);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}