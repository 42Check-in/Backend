package check_in42.backend.notice;

import check_in42.backend.equipments.EquipmentService;
import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.presentation.PresentationService;
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

    @GetMapping("/notice")
    public ResponseEntity<List<NoticeDTO>> showNotice(@CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId).get();
        List<NoticeDTO> noticeslist = noticeService.showNotice(user.getId());
        return ResponseEntity.ok().body(noticeslist);
    }

    @PostMapping("/notice")
    public ResponseEntity checkNotice(@CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId).get();

        noticeService.updateNotice(user.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
