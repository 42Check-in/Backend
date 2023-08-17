package check_in42.backend.notice;

import check_in42.backend.equipments.EquipmentService;
import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.presentation.PresentationService;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.visitors.VisitorsService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final UserService userService;
    private final VisitorsService visitorsService;
    private final PresentationService presentationService;
    private final EquipmentService equipmentService;

    @GetMapping("/notice")
    public ResponseEntity<List<NoticeDTO>> showNotice(@CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);
        List<NoticeDTO> noticeslist = noticeService.showNotice(user.getId());
        return ResponseEntity.ok().body(noticeslist);
    }

    @PostMapping("/notice")
    public ResponseEntity checkNotice(@CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);

        equipmentService.
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
