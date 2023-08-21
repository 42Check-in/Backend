package check_in42.backend.auth.oauth;

import check_in42.backend.auth.argumentresolver.User;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.auth.exception.AuthorizationException;
import check_in42.backend.auth.jwt.TokenPair;
import check_in42.backend.auth.jwt.TokenProvider;
import check_in42.backend.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OauthController {

    private final OauthService oauthService;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private HttpSession httpSession;
    private final TokenProvider tokenProvider;

//    @GetMapping("api/auth/login")
//    public String loginPage(@User UserInfo userInfo) {
//        if (userInfo.getIntraId() == null) {
//            return "redirect:http://42check-in.kr/oauth/login";
//        }
//        return "redirect:http://42check-in.kr/main";
//    }
//    @PostMapping("/oauth/login")
//    @ResponseBody
//    public ResponseEntity seoul42Login(HttpServletRequest request, HttpServletResponse response,
//                                       @RequestParam("code") String code) {
//
//        /*
//        * Token, user값이 모두 없는 경우 String code값을 갖고 oauthToken을 생성.
//        * */
//        OauthToken oauthToken = oauthService.getOauthToken(code);
//        User42Info user42Info = oauthService.get42SeoulInfo(oauthToken.getAccess_token());
//        User user = userService.findByName(user42Info.getLogin());
//
//        if (user == null) {
//
//            user = new User(user42Info.getLogin(), user42Info.isStaff());
//            userService.join(user);
//
//            tokenRepository.saveRefreshToken(user42Info.getLogin(), oauthToken);
//            LoginResponse loginResponse = LoginResponse.builder()
//                                                        .accessToken(tokenProvider.createAccessToken(user42Info.getLogin()))
//                                                        .refreshToken(tokenProvider.createRefreshToken(user42Info.getLogin()))
//                                                        .build();
//            httpSession = request.getSession();
//            httpSession.setAttribute("name", user42Info.getLogin());
//            return ResponseEntity.ok(loginResponse);
//        } else {
//            httpSession = request.getSession(false);
//            if (httpSession == null) {
//                httpSession = request.getSession();
//                httpSession.setAttribute("name", user42Info.getLogin());
////                httpSession.setAttribute("staff", user42Info.isStaff());
//            }
//        }
//
//        //쿠키 부분 어
//        Cookie cookie = new Cookie("intraId", user42Info.getLogin());
//        cookie.setMaxAge(50 * 120);
//        cookie.setPath("/");
//
//        response.addCookie(cookie);
//
//        if (user.isStaff())
//            return new ResponseEntity(HttpStatus.ACCEPTED);
//        return new ResponseEntity(HttpStatus.OK);
//    }
    @PostMapping("/oauth/login")
    public ResponseEntity seoul42Login(@RequestBody String code) {
        log.info("로그인 할꺼니?");
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
                .accessToken(accessToken)
                .refreshToken(loginResponse.getRefreshToken()).build();
        return ResponseEntity.ok(newLoginResponse);
    }
}
