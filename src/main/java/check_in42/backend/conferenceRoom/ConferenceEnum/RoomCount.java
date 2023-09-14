package check_in42.backend.conferenceRoom.ConferenceEnum;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomCount {
    GAEPO(5L),
    SEOCHO(2L);

    private final Long value;
}
