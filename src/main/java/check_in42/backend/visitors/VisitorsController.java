package check_in42.backend.visitors;

import check_in42.backend.user.User;
import check_in42.backend.visitors.visitUtils.VisitorsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class VisitorsController {

    private final UserService userService;
    private final VisitorsService visitorsService;

    @PostMapping("visitors/form")
    public ResponseEntity applyVisitorsForm(@RequestBody VisitorsDTO visitorsDTO,
                                            @CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);
        visitorsService.applyVisitorForm(user, visitorsDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("visitors/cancel")
    public ResponseEntity cancelVisitors(@RequestBody VisitorsDTO visitorsDTO,
                                         @CookieValue(name = "intraId") String intraId) {
        Optional<Visitors> visitors = visitorsService.findById(visitorsDTO.getVisitorsId());
        visitorsService.delete(visitors, intraId);
    }
}
