package check_in42.backend.presentation;

import check_in42.backend.presentation.utils.PresentationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PresentationController {

    private final PresentationService presentationService;

    //새로운 신청 폼 작성
    @PostMapping("/presentations/form")
    public ResponseEntity createNewForm(@CookieValue String intraId, @RequestBody PresentationDTO presentationDTO) {
        Presentation presentation = presentationService.create(intraId, presentationDTO);
        Long formId = presentationService.join(presentation);
        presentationService.addPresentationToUser(intraId, presentation);
        return ResponseEntity.ok().body(formId);
    }

    //수요지식회 신청 현황 조회
    /*
    * 이 로직이 맞묘? 해당 달에 신청한 form들 추려서 dto list로 만들어 쏘기
    * */
    @GetMapping("/presentations")
    public List<PresentationDTO> showList(@RequestParam String month) {
        return presentationService.showMonthSchedule(month);
    }

    //수요지식회 취소
    /*
    * db에서 form 지우고, user에서 list에서도 지웡
    * */
    @PostMapping("/presentations/cancel")
    public ResponseEntity cancel(@CookieValue String intraId, @RequestParam Long formId) {
        presentationService.findAndDelete(intraId, formId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
