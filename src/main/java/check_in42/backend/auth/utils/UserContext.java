package check_in42.backend.auth.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class UserContext {

    private static final String unknown = "Unknown";

    private String intraId = unknown;

    private boolean staff;

    public void setIntraId(final String intraId) {
        this.intraId = intraId;
    }

    public void setStaff(final Boolean staff) {
        this.staff = staff;
    }

    public String getIntraId() {
        return this.intraId;
    }
}
