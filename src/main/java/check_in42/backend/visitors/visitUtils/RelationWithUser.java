package check_in42.backend.visitors.visitUtils;

import check_in42.backend.equipments.utils.EnumUtils;
import check_in42.backend.presentation.utils.PresentationTime;
import lombok.Getter;

@Getter
public enum RelationWithUser {
    ETC("기타 다른 관계 :"),
    CADET("42서울에서 인연을 맺었던 구 동료\"피시너, 카뎃\""),
    FRIEND("나를 보고싶어 개포까지 달려올 나의 \"친구\""),
    FAMILY("나의 소중한 \"가족\"(부모님,형제 등)"),
    TUTOR("멘토님과 상응하는 \"은사\"");

    private String name;

    RelationWithUser(String name) {
        this.name = name;
    }

    public static int getOrdinalByDescription(String description) {
        return EnumUtils.getOrdinalByDescription(PresentationTime.class, description);
    }
}
