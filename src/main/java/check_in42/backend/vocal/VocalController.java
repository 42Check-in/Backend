package check_in42.backend.vocal;

import check_in42.backend.equipments.EquipmentDTO;
import check_in42.backend.equipments.EquipmentService;
import check_in42.backend.notice.NoticeService;
import check_in42.backend.presentation.PresentationService;
import check_in42.backend.presentation.utils.PresentationDTO;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
import check_in42.backend.visitors.VisitorsService;
import check_in42.backend.visitors.visitUtils.VisitorsDTO;
import check_in42.backend.vocal.utils.FormIdList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final NoticeService noticeService;

    //모든 외부인 신청에 대한 조회 이지만, 갯수를 정할지 수락하지 않은 리스트만 보여줄지 정해야할듯
    @GetMapping("/visitors")
    public ResponseEntity allVisitorsApply() {
        final List<VisitorsDTO> visitorsList = visitorsService.findAll();
        return ResponseEntity.ok().body(visitorsList);
    }

    // 전체 수요지식회 목록을 보여주는 기능 월별로 sort?
    /*
    *
    *   보컬의 수요 지식회 신청 목록 status 변경
        대기열 바로 업데이트 원함..

        최상위 → 신청 중
        같은 날에 또 신청 → 대기중
        보컬은 최상위의 1, 2, 3 상태값만 관리
        * 추가 작업이 필요할듯...
    * */
    @GetMapping("/presentations")
    public ResponseEntity allPresentation() {
        List<PresentationDTO> presentationList = presentationService.findAllDESC();
        return ResponseEntity.ok(presentationList);
    }

    // 전체 기자재 신청 목록을 보여주는 기능
    @GetMapping("/equipments")
    public ResponseEntity allEquipment() {
        List<EquipmentDTO> equipmentList = equipmentService.findAllDESC();
        return ResponseEntity.ok(equipmentList);
    }

    // 외부인 신청에 대한 수락
    @PostMapping("/visitors")
    public ResponseEntity confirmVisitorsApply(@RequestBody final FormIdList formIdList) {
//        visitorsService.vocalConfirm(formIdList.getFormIds());
        noticeService.inputNoticeVisitors(formIdList);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // 수요지식회의 신청 상태를 운영진이 설정하는 api
    // 신청중, 대기, 아젠다 등록, 스케쥴 완료, 강의 완료
    // presentationDTO -> List<Long> formIds, PresentationStatus(int) status
    @PostMapping("/presentations")
    public ResponseEntity confirmPresentationApply(@RequestBody final FormIdList formIdList) {
//        presentationService.setAgreeDatesAndStatus(formIdList.getPresenList());
        noticeService.inputNoticePresentations(formIdList);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/equipments")
    public ResponseEntity confirmEquipmentApply(@RequestBody final FormIdList formIdList) {
//        equipmentService.setAgreeDates(formIdList.getFormIds());
        noticeService.inputNoticeEquipments(formIdList);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("visitors/{intraId}")
    public ResponseEntity searchByIntraId(@PathVariable String intraId) {
        final List<VisitorsDTO> visitorsDTOS = userService.findVisitorList(intraId);
        return ResponseEntity.ok(visitorsDTOS);
    }

}
