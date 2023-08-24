package check_in42.backend.auth.utils;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Component
@Order
@SessionScope
public class UserContext {

    private static final String unknown = "Unknown";

    private String intraId = unknown;

    public void setIntraId(final String intraId) {
        this.intraId = intraId;
    }

    public String getIntraId() {
        return this.intraId;
    }
}
