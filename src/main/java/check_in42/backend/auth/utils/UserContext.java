package check_in42.backend.auth.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class UserContext {

    private static final String unknown = "Unknown";

    private String intraId = unknown;

    private boolean staff;

    private String grade;

    public void setIntraId(final String intraId) {
        this.intraId = intraId;
    }

    public void setStaff(final boolean staff) {
        this.staff = staff;
    }

    public void setGrade(final String grade) {
        this.grade = grade;
    }
    public String getIntraId() {
        return this.intraId;
    }
    public boolean isStaff() {
        return this.staff;
    }
    public String getGrade() {
        return this.grade;
    }
}
