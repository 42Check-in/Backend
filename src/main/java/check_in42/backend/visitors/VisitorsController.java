package check_in42.backend.visitors;

import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.visitors.utils.VisitorsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VisitorsController {

    private final VisitorsService visitorsService;
    private final UserService userService;

    @PostMapping("visitors/form")
    public ResponseEntity applyVisitorsForm(@RequestBody VisitorsDTO visitorsDTO,
                                            @CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);
        Visitors visitors = visitorsService.createVisitors(user, visitorsDTO);
        visitorsService.applyVisitorForm(user, visitors);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("visitors/cancel")
    public ResponseEntity cancelVisitors(@RequestBody VisitorsDTO visitorsDTO,
                                         @CookieValue(name = "intraId") String intraId) {
        visitorsService.delete(visitorsDTO, intraId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
