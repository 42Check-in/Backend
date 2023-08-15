package check_in42.backend.visitors.visitUtils;

public enum RelationWithUser {
    CADET(0), FRIEND(1), FAMILY(2), TUTOR(3), ETC(4);

    private int relationship;

    RelationWithUser(int relationship) {
        this.relationship = relationship;
    }
}
