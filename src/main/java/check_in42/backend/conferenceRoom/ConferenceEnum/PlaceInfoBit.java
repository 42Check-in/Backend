package check_in42.backend.conferenceRoom.ConferenceEnum;

public enum PlaceInfoBit {
    CLUSTER(0B11L),
    ROOM(0B111111L),
    TIME(0B111111111111111111111111L);

    private final Long value;

    PlaceInfoBit(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
