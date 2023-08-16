package check_in42.backend.notice;

import check_in42.backend.notice.utils.NoticeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoticeController {

    @GetMapping("/notices")
    public ResponseEntity showNotice(@CookieValue(name = "intraId") String intraId) {

    }

    @PostMapping("/notice")
    public ResponseEntity checkNotice(@CookieValue(name = "intraId") String intraId, @RequestBody NoticeDTO noticeDTO) {

    }
}
