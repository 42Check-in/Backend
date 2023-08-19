package check_in42.backend.visitors.visitUtils;

import lombok.Getter;

@Getter
public enum VisitPlace {
    ETC("기타 장소 :"),
    B1("지하1층 어셈블리"),
    OPEN_STUDIO("1층 오픈스튜디오"),
    CLUSTER("2,4,5층 클러스터"),
    THIRD_FLOOR("3층 회의실"),
    MARU_BUILDING("폴베가 있는 마루관");

    private String placeType;

    VisitPlace(String placeType) {
        this.placeType = placeType;
    }
}
