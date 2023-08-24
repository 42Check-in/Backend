package check_in42.backend.auth.oauth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private boolean staff;

    protected LoginResponse(String accessToken, String refreshToken, boolean staff) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.staff = staff;
    }

}
