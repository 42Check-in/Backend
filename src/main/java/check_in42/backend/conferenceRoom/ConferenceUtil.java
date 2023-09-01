package check_in42.backend.conferenceRoom;

import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfo;
import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfoBit;
import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfoBitSize;

import java.util.Map;

public class ConferenceUtil {
    public static int bitIdx(Long num) {
        int i = 0;

        while ((num & 1) != 1) {
            num = num >> 1;
            i++;
        }
        if (num > 1)
            return -1;
        return i;
    }

    public static Long bitN(Long num) {
        long result = 0L;

        while (num != 0) {
            result += num & 1;
            num = num >> 1;
        }
        return result;
    }

    public static long[] getRooms(Map<String, long[]> clusters, Integer clusterNum) {
        if (clusterNum < 0)
            return null;
        if (clusterNum == PlaceInfo.GAEPO.ordinal())
            return clusters.get(PlaceInfo.GAEPO.getValue());
        if (clusterNum == PlaceInfo.SEOCHO.ordinal())
            return clusters.get(PlaceInfo.SEOCHO.getValue());
        return null;
    }

    public static Long[] setReservationInfo(Long reservationInfoBit) {
        Long[] result = new Long[3];

        result[0] = reservationInfoBit >> (PlaceInfoBitSize.ROOM.getValue() + PlaceInfoBitSize.TIME.getValue());
        result[1] = (reservationInfoBit & PlaceInfoBit.ROOM.getValue()) >> PlaceInfoBitSize.TIME.getValue();
        result[2] = reservationInfoBit & PlaceInfoBit.TIME.getValue();
        return result;
    }
}
