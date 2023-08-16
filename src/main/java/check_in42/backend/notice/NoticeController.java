package check_in42.backend.notice;

import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final UserService userService;

    @GetMapping("/notices")
    public ResponseEntity showNotice(@CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);
        List<NoticeDTO> noticeslist = noticeService.showNotice(user);
        return ResponseEntity.ok(noticeslist);
    }

    @PostMapping("/notice")
    public ResponseEntity checkNotice(@RequestBody List<NoticeDTO> noticeDTO) {
        noticeService.checkNotice(noticeDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
