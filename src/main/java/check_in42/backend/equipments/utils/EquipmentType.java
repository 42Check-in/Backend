package check_in42.backend.equipments.utils;

import lombok.Getter;
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
}
