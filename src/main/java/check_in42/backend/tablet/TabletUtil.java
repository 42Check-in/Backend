package check_in42.backend.tablet;

import java.time.LocalDateTime;

public class TabletUtil {
    public static int getTimeIdx() {
        LocalDateTime now = LocalDateTime.now();

        if (now.getHour() < 8)
            return 0;
        return ((now.getHour() - 8) * 2) + (now.getMinute() >= 30 ? 1 : 0);
    }
}
