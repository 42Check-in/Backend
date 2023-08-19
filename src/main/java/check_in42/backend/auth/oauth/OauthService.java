package check_in42.backend.auth.oauth;

import check_in42.backend.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final ObjectMapper om = new ObjectMapper();
    private final RestTemplate template = new RestTemplate();
    private final UserService userService;
    HttpHeaders headers;
    HttpEntity<MultiValueMap<String, String>> req;
    ResponseEntity<String> res;
    MultiValueMap<String, String> params;

    public OauthToken getOauthToken(String code) {
        req = req42TokenHeader(code);
        res = resPostApi(req, req42TokenUri());

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
        return UriComponentsBuilder.fromHttpUrl("https://api.intra.42.fr/oauth/token")
                .build()
                .toUri();
    }

    private HttpEntity<MultiValueMap<String, String>> req42TokenHeader(String code) {
        headers = new HttpHeaders();
        params = new LinkedMultiValueMap<>();
        headers.set("Content-type", "Application/x-www-form-urlencoded;charset=utf-8");

        params.add("grant_type", "authorization_code");
        params.add("client_id", "u-s4t2ud-3b57ef43b210f8fbf7a0029fa629f976bd0a1506976d74b843eab9f4bafa2727");
        params.add("client_secret", "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");
        params.add("code", code);
        params.add("redirect_uri", "연결할 callback 주소");

        return new HttpEntity<>(params, headers);
    }
}
