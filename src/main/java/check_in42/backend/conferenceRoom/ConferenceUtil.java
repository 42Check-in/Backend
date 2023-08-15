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

    public static Long[] getCluster(Map<String, Long[]> clusters, Integer clusterNum) {
        if (clusterNum == PlaceInfo.GAEPO.ordinal())
            return clusters.get(PlaceInfo.GAEPO.getValue());
        if (clusterNum == PlaceInfo.SEOCHO.ordinal())
            return clusters.get(PlaceInfo.SEOCHO.getValue());
        return null;
    }

    public static Long getDayBit(Long year, Long month) {
        int lastOfMonth;

        if (month > 9)
            lastOfMonth = LocalDate.parse(year + "-" + month + "-01").lengthOfMonth();
        else
            lastOfMonth = LocalDate.parse(year + "-0" + month + "-01").lengthOfMonth();
        if (lastOfMonth == 31)
            return 0B1111111111111111111111111111111L;
        if (lastOfMonth == 30)
            return 0B111111111111111111111111111111L;
        if (lastOfMonth == 29)
            return 0B11111111111111111111111111111L;
        else
            return 0B1111111111111111111111111111L;
    }
}
