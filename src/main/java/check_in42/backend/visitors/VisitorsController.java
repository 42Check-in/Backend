package check_in42.backend.visitors;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.visitors.visitUtils.VisitorVocalResponse;
import check_in42.backend.visitors.visitUtils.VisitorsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
        visitorsService.delete(visitorsDTO.getFormId(), userInfo.getIntraId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("visitors/form/approval")
    public ResponseEntity<VisitorVocalResponse> approvalForm(Pageable pageable) {
        final VisitorVocalResponse visitorVocalResponse = visitorsService.findApprovalVisitorsList(pageable);
        return ResponseEntity.ok(visitorVocalResponse);
    }

    @GetMapping("visitors/form/not-approval")
    public ResponseEntity<VisitorVocalResponse> notApprovalForm(Pageable pageable) {
        final VisitorVocalResponse visitorVocalResponse = visitorsService.findNotApprovalVisitorsList(pageable);
        return ResponseEntity.ok(visitorVocalResponse);
    }

}
