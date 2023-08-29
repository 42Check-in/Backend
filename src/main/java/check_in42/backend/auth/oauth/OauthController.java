package check_in42.backend.auth.oauth;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.auth.exception.AuthorizationException;
import check_in42.backend.auth.jwt.TokenPair;
import check_in42.backend.auth.oauth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OauthController {

    private final OauthService oauthService;

    @PostMapping("/oauth/login")
    public ResponseEntity seoul42Login(@RequestBody final String code) {

        log.info("로그인 할꺼니?");
        log.info(code);
        final TokenPair tokenPair = oauthService.login(code);
        final boolean staff = oauthService.isStaff(tokenPair.getAccessToken());

        final LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(tokenPair.getAccessToken())
                .refreshToken(tokenPair.getRefreshToken())
                .staff(staff)
                .build();
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@UserId final UserInfo userInfo) {
        oauthService.logout(userInfo.getIntraId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity reissueToken(@RequestBody final String refreshToken) {

        log.info("Reissue!!!");
        log.info(refreshToken + "---------------------------------------------");
        if (refreshToken == null) {
            throw new AuthorizationException.RefreshTokenNotFoundException();
        }

        final String accessToken = oauthService.reissueToken(refreshToken);
        final boolean staff = oauthService.isStaff(accessToken);

        final LoginResponse newLoginResponse = LoginResponse.builder()
                .accessToken(accessToken)
                .staff(staff)
                .build();
        return ResponseEntity.ok(newLoginResponse);
    }
}
