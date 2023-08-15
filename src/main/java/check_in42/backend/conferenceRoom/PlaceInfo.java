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
    GAEPO(5),
    SEOCHO(2);

    private final int value;

    RoomCount(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

enum PlaceInfoBit {
    CLUSTER(0B11),
    ROOM(0B111111),
    TIME(0B111111111111111111111111);

    private final int value;

    PlaceInfoBit(int value) {
        this.value = value;
    }

    public int getValue() {
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