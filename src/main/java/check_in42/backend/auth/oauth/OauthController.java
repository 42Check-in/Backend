package check_in42.backend.auth.oauth;

import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;
    private final UserService userService;
    @GetMapping("login")
    public ResponseEntity loginPage(HttpServletRequest request,
                            @CookieValue(name = "intraId", required = false) String intraId) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            if (intraId == null) {
                /*
                * 1. String으로 반환값 변경
                * 2. return값들도 발급받은 redir 주소로 변경, Define 추후 설정 필요
                * */
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("login")
    public ResponseEntity seoul42Login(HttpServletRequest request, HttpServletResponse response, @RequestParam("code") String code) {

        /*
        * Token, user값이 모두 없는 경우 String code값을 갖고 oauthToken을 생성.
        * */
        OauthToken oauthToken = oauthService.getOauthToken(code);
        User42Info user42Info = oauthService.get42SeoulInfo(oauthToken.getAccess_token());
        User user = userService.findByName(user42Info.getLogin());


    }
}
