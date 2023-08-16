package check_in42.backend.conferenceRoom;

import java.time.LocalDate;
import java.util.Map;

public class ConferenceUtil {
    public static Integer BitIdx(Long num) {
        Integer i = 0;

        while (num != 1) {
            num = num >> 1;
            i++;
        }
        return i;
    }

    public static Long BitN(Long num) {
        Long result = 0L;

        while (num != 0) {
            result += num & 1;
            num = num >> 1;
        }
        return result;
    }

    public static Long[] getRooms(Map<String, Long[]> clusters, Integer clusterNum) {
        if (clusterNum == PlaceInfo.GAEPO.ordinal())
            return clusters.get(PlaceInfo.GAEPO.getValue());
        if (clusterNum == PlaceInfo.SEOCHO.ordinal())
            return clusters.get(PlaceInfo.SEOCHO.getValue());
        return null;
    }

    public static int getLastDayOfMonth(Long year, Long month) {
        if (month > 9)
            return LocalDate.parse(year + "-" + month + "-01").lengthOfMonth();
        return LocalDate.parse(year + "-0" + month + "-01").lengthOfMonth();
    }

    public static Long getDayBit(Long year, Long month) {
        int lastOfMonth;

        lastOfMonth = getLastDayOfMonth(year, month);
        if (lastOfMonth == 31)
            return 0B1111111111111111111111111111111L;
        if (lastOfMonth == 30)
            return 0B111111111111111111111111111111L;
        if (lastOfMonth == 29)
            return 0B11111111111111111111111111111L;
        else
            return 0B1111111111111111111111111111L;
    }

    public static Long[] setReservationInfo(Long reservationInfoBit) {
        Long[] result = new Long[3];

        result[0] = reservationInfoBit >> (PlaceInfoBitSize.ROOM.getValue() + PlaceInfoBitSize.TIME.getValue());
        result[1] = (reservationInfoBit >> PlaceInfoBitSize.TIME.getValue()) & PlaceInfoBit.ROOM.getValue();
        result[2] = reservationInfoBit & PlaceInfoBit.TIME.getValue();
        return result;
    }

    public static Boolean isSamePlace(Long[] reservationInfo, Long[] reqFormReservationInfo) {
        if (reservationInfo[0] == reqFormReservationInfo[0] &&
            reservationInfo[1] == reqFormReservationInfo[1])
            return true;
        return false;
    }
}
