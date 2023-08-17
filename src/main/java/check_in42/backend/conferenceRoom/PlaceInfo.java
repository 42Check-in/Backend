package check_in42.backend.conferenceRoom;

public enum PlaceInfo {
    GAEPO("gaepo"),
    SEOCHO("seocho");

    private final String value;

    PlaceInfo(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}

enum RoomCount {
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

enum PlaceInfoBit {
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

enum PlaceInfoBitSize {
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