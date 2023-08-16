package check_in42.backend.main;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @GetMapping("/notices")
    public ResponseEntity showNotice(@CookieValue(name = "intraId") String intraId) {

    }

    @PostMapping("/notice")
    public ResponseEntity checkNotice(@CookieValue(name = "intraId") String intraId, @RequestBody NoticeDTO noticeDTO) {

    }

    @PostMapping
}
