package check_in42.backend.tablet;

import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfoBitSize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rooms {
    CLUSTER1_1("cluster1_1", 0B01000001L << PlaceInfoBitSize.TIME.getValue()),
    CLUSTER1_2("cluster1_2", 0B01000010L << PlaceInfoBitSize.TIME.getValue()),
    CLUSTER_X("cluster_x", 0B01000100L << PlaceInfoBitSize.TIME.getValue()),
    CLUSTER3_1("cluster3_1", 0B01001000L << PlaceInfoBitSize.TIME.getValue()),
    CLUSTER3_2("cluster3_2", 0B01010000L << PlaceInfoBitSize.TIME.getValue()),
    CLUSTER7("cluster7", 0B10000001L << PlaceInfoBitSize.TIME.getValue()),
    CLUSTER9("cluster9", 0B10000010L << PlaceInfoBitSize.TIME.getValue());

    private final String roomName;
    private final Long roomBit;
}
