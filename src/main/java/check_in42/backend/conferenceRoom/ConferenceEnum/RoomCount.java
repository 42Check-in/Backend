package check_in42.backend.conferenceRoom.ConferenceEnum;

public enum RoomCount {
    GAEPO(5L),
    SEOCHO(2L);

    private final Long value;

    RoomCount(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
