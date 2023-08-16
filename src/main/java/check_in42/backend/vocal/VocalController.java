package check_in42.backend.vocal;

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

    @GetMapping("visitors")
    public ResponseEntity allVisitorsApply() {
            List<Visitors> visitorsList = visitorsService.findAll();
        return ResponseEntity.ok().body(visitorsList);
    }

    @GetMapping("presentations")
    public ResponseEntity allPresentation() {

    }

    @GetMapping("equipments")
    public ResponseEntity allEquipment() {

    }

    // 외부인 신청에 대한 수락
    @PostMapping("visitors")
    public ResponseEntity confirmVisitorsApply(@RequestParam List<Long> formId) {
        visitorsService.vocalConfirm(formId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // 수요지식회의 신청 상태를 운영진이 설정하는 api
    // 신청중, 대기, 아젠다 등록, 스케쥴 완료, 강의 완료
    @PostMapping("presentations/status")
    public ResponseEntity confirmPresentationApply() {

    }

    @PostMapping("equipments")
    public ResponseEntity confirmEquipmentApply() {

    }

}
