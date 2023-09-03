package check_in42.backend.conferenceRoom.ConferenceEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceInfoBitSize {
    CLUSTER(2),
    ROOM(6),
    TIME(24);

    private final int value;
}
