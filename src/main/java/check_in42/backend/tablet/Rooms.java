package check_in42.backend.tablet;

import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfoBitSize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rooms {
    CLUSTER1_1(0B01000001L << PlaceInfoBitSize.TIME.getValue()),
    CLUSTER1_2(0B01000010L << PlaceInfoBitSize.TIME.getValue()),
    CLUSTER_X(0B01000100L << PlaceInfoBitSize.TIME.getValue()),
    CLUSTER3_1(0B01001000L << PlaceInfoBitSize.TIME.getValue()),
    CLUSTER3_2(0B01010000L << PlaceInfoBitSize.TIME.getValue()),
    CLUSTER7(0B10000001L << PlaceInfoBitSize.TIME.getValue()),
    CLUSTER9(0B10000010L << PlaceInfoBitSize.TIME.getValue());

    private final Long roomBit;
}
