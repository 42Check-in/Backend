package check_in42.backend.conferenceRoom;

import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDay;
import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDayService;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import check_in42.backend.myCheckIn.MyCheckInService;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conference-room/")
public class ConferenceController {
    private final ConferenceRoomService conferenceRoomService;
    private final ConferenceCheckDayService conferenceCheckDayService;

    private final MyCheckInService myCheckInService;
    private final UserService userService;

    @GetMapping("calender/{year}/{month}")
    public ResponseEntity<Long> calender(@PathVariable(name = "year") Long year, @PathVariable(name = "month") Long month) {
        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayService.findByDate(year, month);

        if (conferenceCheckDay != null)
            return ResponseEntity.ok().body(0L);
        return ResponseEntity.ok().body(conferenceCheckDay.getDays());
    }

    @GetMapping("place-time/{day}")
    public ResponseEntity<Map<String, Long[]>> placeTime(@PathVariable(name = "day") Long day) {
        Map<String, Long[]> result = conferenceRoomService.makeBase();

        conferenceRoomService.setReservedInfo(result, day);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("form")
    public ResponseEntity inputForm(@RequestBody ConferenceRoomDTO conferenceRoomDTO, @CookieValue(name = "intraId") String intraId) {
        if (!conferenceRoomService.isInputForm(conferenceRoomDTO))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        User user = userService.findByName(intraId);
        ConferenceRoom conferenceRoom = conferenceRoomService.create(conferenceRoomDTO, user);
        conferenceRoomService.join(conferenceRoom);
        user.addConferenceForm(conferenceRoom);

        if (conferenceRoomService.isDayFull(conferenceRoomDTO.getDate().toString()))
            conferenceCheckDayService.updateDenyCheckDay(conferenceRoomDTO.getDate());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("cancel")
    public ResponseEntity cancelForm(@CookieValue("intraId") String intraId, @RequestBody Long formId) {
        User user = userService.findByName(intraId);
        ConferenceRoomDTO conferenceRoomDTO = myCheckInService.findConferenceFormFromUser(user, formId);

        conferenceCheckDayService.updateAllowCheckDay(conferenceRoomDTO.getDate());
        conferenceRoomService.deleteById(user, formId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}