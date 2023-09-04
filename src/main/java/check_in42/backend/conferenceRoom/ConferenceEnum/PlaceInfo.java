package check_in42.backend.conferenceRoom.ConferenceEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceInfo {
    GAEPO("gaepo"),
    SEOCHO("seocho");

    private final String value;
}