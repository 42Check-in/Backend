package check_in42.backend.visitors.visitUtils;

import check_in42.backend.equipments.utils.EnumUtils;
import check_in42.backend.equipments.utils.EquipmentType;
import lombok.Getter;

@Getter
public enum VisitPurpose {
    ETC("기타 목적 :"),
    FIELD_TRIP("견학: \"너희 교육장이 정말 궁금하구나!\""),
    STUDYING("학습: \"너와 함께 공부하고 싶어!\""),
    TALKING("토크: \"이야기할 것이 많으니 교육장 안에서 이야기하자\"");

    private String name;

    VisitPurpose(String name) {
        this.name = name;
    }

    public static int getOrdinalByDescription(String description) {
        return EnumUtils.getOrdinalByDescription(VisitPurpose.class, description);
    }
}
