package check_in42.backend.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OauthController {

    @GetMapping("login")
    public ResponseEntity loginPage(HttpServletRequest request,
                            @CookieValue(name = "intraId", required = false) String intraId) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            if (intraId == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
//    @PostMapping("login")
//    public ResponseEntity seoul42Login(@RequestParam("code") String code) {
//
//    }
}
