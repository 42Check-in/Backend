package check_in42.backend.visitors.visitUtils;

public enum VisitPlace {
    B1(0), OPEN_STUDIO(1), CLUSTER(2), THIRD_FLOOR(3), MARU_BUILDING(4);

    private int place;

    VisitPlace(int place) {
        this.place = place;
    }
}
