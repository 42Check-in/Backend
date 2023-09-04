package check_in42.backend.presentation;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.presentation.utils.PresentationDTO;
import check_in42.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PresentationController {

    private final PresentationService presentationService;
    private final UserService userService;

    @PostMapping("/presentations/form")
    public ResponseEntity createNewForm(@UserId final UserInfo userInfo,
                                        @RequestBody final PresentationDTO presentationDTO) {
        final Long formId = presentationService.createNewForm(userInfo.getIntraId(), presentationDTO);
        return ResponseEntity.ok(formId);
    }

    @GetMapping("/presentations")
    public ResponseEntity<List<PresentationDTO>> showList(@RequestParam final String month) {
        List<PresentationDTO> res = presentationService.showMonthSchedule(month);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/presentations/cancel")
    public ResponseEntity cancel(@UserId final UserInfo userInfo,
                                 @RequestBody final PresentationDTO presentationDTO) {
        presentationService.findAndDelete(userInfo.getIntraId(), presentationDTO.getFormId());
        return new ResponseEntity(HttpStatus.OK);
    }

}
