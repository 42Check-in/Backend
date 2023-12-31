package check_in42.backend.auth.oauth;

import check_in42.backend.auth.exception.AuthorizationException;
import check_in42.backend.auth.jwt.TokenPair;
import check_in42.backend.auth.jwt.TokenProvider;
import check_in42.backend.auth.oauth.dto.OauthToken;
import check_in42.backend.auth.oauth.dto.User42Info;
import check_in42.backend.user.User;
import check_in42.backend.user.UserRepository;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class OauthService {

    private final ObjectMapper om = new ObjectMapper();
    private final RestTemplate template = new RestTemplate();
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    HttpHeaders headers;
    HttpEntity<MultiValueMap<String, String>> req;
    ResponseEntity<String> res;
    MultiValueMap<String, String> params;


    public OauthToken getOauthToken(String code) {
        req = req42TokenHeader(code);
        log.info(code);
        res = resPostApi(req, req42TokenUri());
        log.info("뭐냐고");
        return readOauthToken(res.getBody());
    }

    public User42Info get42SeoulInfo(String token) {
        req = req42ApiHeader(token);
        res = resGetApi(req, req42UserUri());
        return readUser42Info(res.getBody());
    }

    private User42Info readUser42Info(String body) {
        User42Info user42Info = null;
        try {
            user42Info = om.readValue(body, User42Info.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return user42Info;
    }

    private ResponseEntity<String> resGetApi(HttpEntity<MultiValueMap<String, String>> req, URI uri) {
        return template.exchange(uri.toString(),
                HttpMethod.GET,
                req,
                String.class);
    }

    private URI req42UserUri() {
        return UriComponentsBuilder.newInstance().scheme("https://api.intra.42.fr").path("/v2/me")
                .build()
                .toUri();
    }

    private HttpEntity<MultiValueMap<String, String>> req42ApiHeader(String token) {
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Accept", "application/json;charset=utf-8");
        params = new LinkedMultiValueMap<>();
        return new HttpEntity<>(params, headers);
    }

    private OauthToken readOauthToken(String body) {
        OauthToken oauthToken = null;
        try {
            oauthToken = om.readValue(body, OauthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return oauthToken;

    }

    private ResponseEntity<String> resPostApi(HttpEntity<MultiValueMap<String, String>> req, URI uri) {
        return template.exchange(uri.toString(),
                HttpMethod.POST,
                req,
                String.class);
    }

    private URI req42TokenUri() {
        log.info("in req42TokenUri?????????????????");
        return UriComponentsBuilder.fromHttpUrl("https://api.intra.42.fr/oauth/token")
                .build()
                .toUri();
    }

    private HttpEntity<MultiValueMap<String, String>> req42TokenHeader(final String code) {
        headers = new HttpHeaders();
        params = new LinkedMultiValueMap<>();
        headers.set("Content-type", "Application/x-www-form-urlencoded;charset=utf-8");

        params.add("grant_type", "authorization_code");
        params.add("client_id", "u-s4t2ud-216dca40c46a9ceef4ce38bfb3891a961cc8270d51701347321b4c68ffd70be3");
        params.add("client_secret", "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");
        params.add("code", code);
        params.add("redirect_uri", "https://42check-in.kr/oauth/login");

        return new HttpEntity<>(params, headers);
    }

    @Transactional
    public TokenPair login(final String code) {
        final OauthToken oauthToken = this.getOauthToken(code);
        log.info("오어스 너 머냐고");
        final User42Info user42Info = this.get42SeoulInfo(oauthToken.getAccess_token());

        final String intraId = user42Info.getLogin();
        final boolean staff = user42Info.isStaff();
        final String grade = user42Info.getCursus_users().get(1).getGrade();
        log.info("-----------grade????" + grade);
        final String accessToken = tokenProvider.createAccessToken(intraId);
        final String refreshToken = tokenProvider.createRefreshToken(intraId);
        userService.findByName(intraId)
                .ifPresentOrElse(user -> user.setRefreshToken(refreshToken),
                        () -> userService.create(intraId, staff, refreshToken, grade));
        return new TokenPair(accessToken, refreshToken);
    }


    @Transactional
    public void logout(final String intraId) {
        final User user = userRepository.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        user.setRefreshToken(null);
    }

    public boolean isStaff(final String accessToken) {
        final Claims claims = tokenProvider.parseAccessTokenClaim(accessToken);
        final String intraId = claims.get("intraId", String.class);

        final User user = userService.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final boolean staff = user.isStaff();

        return staff;
    }

    public String getGrade(final String accessToken) {
        final Claims claims = tokenProvider.parseAccessTokenClaim(accessToken);
        final String intraId = claims.get("intraId", String.class);

        final User user = userService.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final String grade = user.getGrade();

        return grade;
    }


    public String reissueToken(final String refreshToken) {
        final Claims claims = tokenProvider.parseRefreshTokenClaim(refreshToken);
        userService.findByRefreshToken(refreshToken)
                .orElseThrow(AuthorizationException.RefreshTokenNotFoundException::new);
        final String intraId = claims.get("intraId", String.class);

        return tokenProvider.createAccessToken(intraId);
    }
}
