package check_in42.backend.visitors;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
import check_in42.backend.visitors.visitUtils.VisitorsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VisitorsController {

    private final VisitorsService visitorsService;

    @PostMapping("visitors/form")
    public ResponseEntity applyVisitorsForm(@RequestBody final VisitorsDTO visitorsDTO,
                                            @UserId final UserInfo userInfo) {
        visitorsService.applyVisitorForm(userInfo.getIntraId(), visitorsDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("visitors/cancel")
    public ResponseEntity cancelVisitors(@RequestBody final VisitorsDTO visitorsDTO,
                                         @UserId final UserInfo userInfo) {
        visitorsService.delete(visitorsDTO, userInfo.getIntraId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
