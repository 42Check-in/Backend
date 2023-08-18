package check_in42.backend.conferenceRoom.ConferenceEnum;

public enum PlaceInfoBitSize {
    CLUSTER(2),
    ROOM(6),
    TIME(24);

    private final int value;

    PlaceInfoBitSize(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
