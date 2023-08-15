package check_in42.backend.visitors.visitUtils;

public enum VisitPurpose {
    FIELD_TRIP(0), STUDYING(1), TALKING(2), ETC(3);

    private int purpose;

    VisitPurpose(int purpose) {
        this.purpose = purpose;
    }
}
