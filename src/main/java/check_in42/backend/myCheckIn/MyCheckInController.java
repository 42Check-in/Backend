package check_in42.backend.myCheckIn;

import check_in42.backend.conferenceRoom.ConferenceRoom;
import check_in42.backend.equipments.Equipment;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
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
    @GetMapping("/conference-room")
    public ResponseEntity myConferenceRooms(@CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);
        final List<ConferenceRoom> conferenceRoomList = user.getConferenceRooms();
        return ResponseEntity.ok(conferenceRoomList);
    }

    @GetMapping("/visitors")
    public ResponseEntity myVisitors(@CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);
        final List<Visitors> visitorsList = user.getVisitors();
        return ResponseEntity.ok(visitorsList);
    }


    @GetMapping("/presentation")
    public ResponseEntity myPresentations(@CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);
        final List<Presentation> presentationList = user.getPresentations();
        return ResponseEntity.ok(presentationList);
    }

    @GetMapping("/equipment")
    public ResponseEntity myEquipments(@CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);
        final List<Equipment> equipmentList = user.getEquipments();
        return ResponseEntity.ok(equipmentList);
    }

    @GetMapping("/conference-room/{formId}")
    public ResponseEntity conferenceRoomForm(@PathVariable Long formId, @CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);
        final ConferenceRoom conferenceRoom = user.findConferensById(formId);
        return ResponseEntity.ok(conferenceRoom);
    }

    @GetMapping("/visitors/{formId}")
    public ResponseEntity visitorForm(@PathVariable Long formId, @CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);
        final Visitors visitors = user.findVisitorsFormById(formId);
        return ResponseEntity.ok(visitors);
    }

    @GetMapping("/presentation/{formId}")
    public ResponseEntity presentationForm(@PathVariable Long formId, @CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);
        final Presentation presentation = user.findPresentationFormById(formId);
        return ResponseEntity.ok(presentation);
    }

    @GetMapping("/equipment/{formId}")
    public ResponseEntity visitorForm(@PathVariable Long formId, @CookieValue(name = "intraId") String intraId) {
        User user = userService.findByName(intraId);
        final Equipment equipment = user.findequipFormById(formId);
        return ResponseEntity.ok(equipment);
    }
}
