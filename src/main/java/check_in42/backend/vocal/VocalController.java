package check_in42.backend.vocal;

import check_in42.backend.equipments.Equipment;
import check_in42.backend.equipments.EquipmentService;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.presentation.PresentationService;
import check_in42.backend.visitors.Visitors;
import check_in42.backend.visitors.VisitorsService;
import check_in42.backend.vocal.utils.AppCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vocal/subscriptions")
public class VocalController {

    private final VisitorsService visitorsService;
    private final PresentationService presentationService;
    private final EquipmentService equipmentService;

    //모든 외부인 신청에 대한 조회 이지만, 갯수를 정할지 수락하지 않은 리스트만 보여줄지 정해야할듯
    @GetMapping("/visitors")
    public ResponseEntity allVisitorsApply() {
            List<Visitors> visitorsList = visitorsService.findAll();
        return ResponseEntity.ok().body(visitorsList);
    }

    // 전체 수요지식회 목록을 보여주는 기능 월별로 sort?
    @GetMapping("/presentations")
    public ResponseEntity allPresentation() {
        List<Presentation> presentationList = presentationService.findAll();
        return ResponseEntity.ok(presentationList);
    }

    // 전체 기자재 신청 목록을 보여주는 기능
    @GetMapping("/equipments")
    public ResponseEntity allEquipment() {
        List<Equipment> equipmentList = equipmentService.findAll();
        return ResponseEntity.ok(equipmentList);
    }

    // 외부인 신청에 대한 수락
    @PostMapping("/visitors")
    public ResponseEntity confirmVisitorsApply(@RequestParam List<Long> formId) {
        visitorsService.vocalConfirm(formId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // 수요지식회의 신청 상태를 운영진이 설정하는 api
    // 신청중, 대기, 아젠다 등록, 스케쥴 완료, 강의 완료
    @PostMapping("/presentations/status")
    public ResponseEntity confirmPresentationApply() {

    }

    @PostMapping("/equipments")
    public ResponseEntity confirmEquipmentApply() {

    }

}
