package check_in42.backend.notice;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
    private final UserService userService;

    @GetMapping("/notice")
    public ResponseEntity<List<NoticeDTO>> showNotice(@UserId final UserInfo userInfo) {
        return ResponseEntity.ok().body(noticeService.showNotice(userInfo.getIntraId()));
    }

    @PostMapping("/notice")
    public ResponseEntity checkNotice(@UserId final UserInfo userInfo) {
        noticeService.updateNotice(userInfo.getIntraId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
