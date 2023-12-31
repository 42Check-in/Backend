package check_in42.backend.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenPair {

    private String accessToken;
    private String refreshToken;
}
