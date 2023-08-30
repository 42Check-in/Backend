package check_in42.backend.equipments.utils;

import check_in42.backend.presentation.utils.PresentationTime;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

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


    public static int getOrdinalByDescription(String description) {
        return EnumUtils.getOrdinalByDescription(EquipmentType.class, description);
    }
}
