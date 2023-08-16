package check_in42.backend.presentation;

import check_in42.backend.presentation.utils.PresentationDTO;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
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
    private final UserService userService;

    //새로운 신청 폼 작성
    @PostMapping("/presentations/form")
    public ResponseEntity createNewForm(@CookieValue String intraId, @RequestBody PresentationDTO presentationDTO) {
        User user = userService.findByName(intraId);
        Presentation presentation = presentationService.create(user, presentationDTO);
        Long formId = presentationService.join(presentation);
        user.addPresentationForm(presentation);
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
