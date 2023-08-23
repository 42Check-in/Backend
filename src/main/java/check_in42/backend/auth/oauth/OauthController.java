package check_in42.backend.auth.oauth;

import check_in42.backend.auth.exception.AuthorizationException;
import check_in42.backend.auth.jwt.TokenPair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OauthController {

    private final OauthService oauthService;

    @PostMapping("/oauth/login")
    public ResponseEntity seoul42Login(@RequestBody String code) {

        log.info("로그인 할꺼니?");
        log.info(code);
        final TokenPair tokenPair = oauthService.login(code);

        final LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(tokenPair.getAccessToken())
                .refreshToken(tokenPair.getRefreshToken())
                .build();
        log.info(loginResponse.getAccessToken(), loginResponse.getRefreshToken());
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/reissue")
    public ResponseEntity reissueToken(@RequestBody final LoginResponse loginResponse) {

        if (loginResponse.getRefreshToken() == null) {
            throw new AuthorizationException.RefreshTokenNotFoundException();
        }

        final String accessToken = oauthService.reissueToken(loginResponse.getRefreshToken());

        final LoginResponse newLoginResponse = LoginResponse.builder()
                .accessToken(accessToken).build();

        return ResponseEntity.ok(newLoginResponse);
    }
}
