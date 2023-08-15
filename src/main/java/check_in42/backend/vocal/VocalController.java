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

    @GetMapping("presentation")
    public ResponseEntity allPresentation() {

    }

    @GetMapping("equipment")
    public ResponseEntity allEquipment() {

    }

    @PostMapping("visitors")
    public ResponseEntity confirmVisitorsApply(@RequestParam List<Long> formId) {
        visitorsService.vocalConfirm(formId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("presentations/status")
    public ResponseEntity confirmPresentationApply() {

    }

    @PostMapping("equipments")
    public ResponseEntity confirmEquipmentApply() {

    }

}
