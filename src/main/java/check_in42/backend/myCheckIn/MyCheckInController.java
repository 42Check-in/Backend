package check_in42.backend.myCheckIn;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.equipments.Equipment;
import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
import check_in42.backend.visitors.Visitors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-checkin")
@RequiredArgsConstructor
public class MyCheckInController {

    private final UserService userService;
    private final MyCheckInService myCheckInService;

    @GetMapping("/conference-room")
    public ResponseEntity myConferenceRooms(@UserId final UserInfo userInfo) {
        final User user = userService.findByName(userInfo.getIntraId())
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<ConferenceRoom> conferenceRoomList = user.getConferenceRooms();
        return ResponseEntity.ok(conferenceRoomList);
    }

    @GetMapping("/visitors")
    public ResponseEntity myVisitors(@UserId final UserInfo userInfo) {
        final User user = userService.findByName(userInfo.getIntraId())
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Visitors> visitorsList = user.getVisitors();
        return ResponseEntity.ok(visitorsList);
    }


    @GetMapping("/presentation")
    public ResponseEntity myPresentations(@UserId final UserInfo userInfo) {
        final User user = userService.findByName(userInfo.getIntraId())
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Presentation> presentationList = user.getPresentations();
        return ResponseEntity.ok(presentationList);
    }

    @GetMapping("/equipment")
    public ResponseEntity myEquipments(@UserId final UserInfo userInfo) {
        final User user = userService.findByName(userInfo.getIntraId())
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Equipment> equipmentList = user.getEquipments();
        return ResponseEntity.ok(equipmentList);
    }

    @GetMapping("/conference-room/{formId}")
    public ResponseEntity conferenceRoomForm(@PathVariable final Long formId,
                                             @UserId final UserInfo userInfo) {
        return ResponseEntity.ok(myCheckInService
                .findConferenceFormFromUser(userService.findByName(userInfo.getIntraId())
                        .orElseThrow(UserRunTimeException.NoUserException::new),
                        formId));
    }

    @GetMapping("/visitors/{formId}")
    public ResponseEntity visitorForm(@UserId final UserInfo userInfo,
                                      @RequestBody final NoticeDTO noticeDTO) {
        return ResponseEntity.ok(myCheckInService
                .findVisitorFormFromUser(userService.findByName(userInfo.getIntraId())
                        .orElseThrow(UserRunTimeException.NoUserException::new), noticeDTO));
    }

    @GetMapping("/presentation/{formId}")
    public ResponseEntity presentationForm(@UserId final UserInfo userInfo,
                                           @RequestBody final NoticeDTO noticeDTO) {
        return ResponseEntity.ok(myCheckInService
                .findPresentationFormFromUser(userService.findByName(userInfo.getIntraId())
                        .orElseThrow(UserRunTimeException.NoUserException::new), noticeDTO));

    }

    @GetMapping("/equipment/{formId}")
    public ResponseEntity equipmentForm(@UserId final UserInfo userInfo,
                                        @RequestBody final NoticeDTO noticeDTO) {
        return ResponseEntity.ok(myCheckInService
                .findEquipFormFromUser(userService.findByName(userInfo.getIntraId())
                        .orElseThrow(UserRunTimeException.NoUserException::new), noticeDTO));
    }

}
