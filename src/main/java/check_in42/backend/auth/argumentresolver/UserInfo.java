package check_in42.backend.auth.argumentresolver;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@Data
public class UserInfo {
    private String intraId;
    private boolean staff;
    private String grade;

    protected UserInfo(final String intraId, final boolean staff, final String grade) {
        this.intraId = intraId;
        this.staff = staff;
        this.grade = grade;
    }
}
