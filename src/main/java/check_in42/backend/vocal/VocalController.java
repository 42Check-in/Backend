package check_in42.backend.vocal;

import check_in42.backend.equipments.utils.EquipmentDTO;
import check_in42.backend.equipments.EquipmentService;
import check_in42.backend.equipments.utils.ResponseEquipment;
import check_in42.backend.presentation.PresentationService;
import check_in42.backend.presentation.utils.ResponsePresentation;
import check_in42.backend.user.UserService;
import check_in42.backend.visitors.VisitorsService;
import check_in42.backend.visitors.visitUtils.VisitorVocalResponse;
import check_in42.backend.visitors.visitUtils.VisitorsDTO;
import check_in42.backend.vocal.utils.FormIdList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/vocal/subscriptions")
public class VocalController {

    private final VisitorsService visitorsService;
    private final PresentationService presentationService;
    private final EquipmentService equipmentService;
    private final UserService userService;

    //모든 외부인 신청에 대한 조회 이지만, 갯수를 정할지 수락하지 않은 리스트만 보여줄지 정해야할듯
    @GetMapping("/visitors")
    public ResponseEntity allVisitorsApply() {
        final List<VisitorsDTO> visitorsList = visitorsService.findAll();
        return ResponseEntity.ok().body(visitorsList);
    }

    @GetMapping("/presentations/form/approval")
    public ResponseEntity allApprovalPresentation(Pageable pageable) {
        final ResponsePresentation presentationList =
                presentationService.findAllApprovalPresentation(pageable);
        return ResponseEntity.ok(presentationList);
    }

    @GetMapping("/presentations/form/not-approval")
    public ResponseEntity allNotApprovalPresentation(Pageable pageable) {
        final ResponsePresentation presentation =
                presentationService.findAllNotApprovalPresentation(pageable);
        return ResponseEntity.ok(presentation);

    }


    // 전체 기자재 신청 목록을 보여주는 기능
    @GetMapping("/equipments/form/approval")
    public ResponseEntity allApprovalEquipment(Pageable pageable) {
        final ResponseEquipment equipmentList = equipmentService.findAllApproval(pageable);
        return ResponseEntity.ok(equipmentList);
    }

    @GetMapping("/equipments/form/not-approval")
    public ResponseEntity allNotApprovalEquipment(Pageable pageable) {
        final ResponseEquipment equipmentList =
                equipmentService.findAllNotApproval(pageable);
        return ResponseEntity.ok(equipmentList);
    }

    // 외부인 신청에 대한 수락
    @PostMapping("/visitors")
    public ResponseEntity confirmVisitorsApply(@RequestBody final FormIdList formIdList) {
        visitorsService.vocalConfirm(formIdList.getFormIds());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/presentations")
    public ResponseEntity confirmPresentationApply(@RequestBody final FormIdList formIdList) {
        presentationService.setAgreeDatesAndStatus(formIdList.getPresenList());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/equipments")
    public ResponseEntity confirmEquipmentApply(@RequestBody final FormIdList formIdList) {
        equipmentService.setAgreeDates(formIdList.getFormIds());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("visitors/{intraId}")
    public ResponseEntity searchByIntraId(@PathVariable String intraId) {
        final List<VisitorsDTO> visitorsDTOS = userService.findVisitorList(intraId);
        return ResponseEntity.ok(visitorsDTOS);
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
