package check_in42.backend.conferenceRoom.ConferenceEnum;

public enum PlaceInfoBit {
    CLUSTER(0B11000000000000000000000000000000L),
    ROOM(0B111111000000000000000000000000L),
    TIME(0B111111111111111111111111L);

    private final Long value;

    PlaceInfoBit(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
