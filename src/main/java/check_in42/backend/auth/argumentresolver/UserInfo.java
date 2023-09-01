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
    protected UserInfo(String intraId, boolean staff) {
        this.intraId = intraId;
        this.staff = staff;
    }
}
