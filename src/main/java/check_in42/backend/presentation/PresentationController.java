package check_in42.backend.presentation;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.presentation.utils.PresentationDTO;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PresentationController {

    private final PresentationService presentationService;
    private final UserService userService;

    //새로운 신청 폼 작성
    /*
    * Date를 갖고 db 순회, List.size가 0인가?
    *
    * 1. 0이다. 해당 요일에 신청자가 아무도 없다 -> approval, status 세팅
    * 2. 0이 아니다. 해당 요일에 신청자가 이미 있다 -> status 세팅
    * */
    @PostMapping("/presentations/form")
    public ResponseEntity createNewForm(@UserId final UserInfo userInfo,
                                        @RequestBody final PresentationDTO presentationDTO) {
        User user = userService.findByName(userInfo.getIntraId())
                .orElseThrow(UserRunTimeException.NoUserException::new);
        int count = presentationService.findByDate(presentationDTO.getDate()).size();
        Presentation presentation = presentationService.create(user, presentationDTO, count);
        Long formId = presentationService.join(presentation);
        user.addPresentationForm(presentation);
        return ResponseEntity.ok().body(formId);
    }

    //수요지식회 신청 현황 조회
    /*
    * 이 로직이 맞묘? 해당 달에 신청한 form들 추려서 dto list로 만들어 쏘기
    * */
    @GetMapping("/presentations")
    public ResponseEntity<List<PresentationDTO>> showList(@RequestParam final String month) {
        log.info("month????????????" + month);
        List<PresentationDTO> res = presentationService.showMonthSchedule(month);
        return ResponseEntity.ok().body(res);
    }

    //수요지식회 취소
    /*
    * db에서 form 지우고, user에서 list에서도 지웡
    * 1. 지운 사람의 status가 '신청중'이다
    * 2. 해당 date의 '대기중'들 중에서 id값이 가장 작은걸 '신청중'으로 변경시킨다.
    *
    * */
    @PostMapping("/presentations/cancel")
    public ResponseEntity cancel(@UserId final UserInfo userInfo,
                                 @RequestParam final Long formId) {
        presentationService.findAndDelete(userInfo.getIntraId(), formId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
