package check_in42.backend.conferenceRoom.ConferenceEnum;

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