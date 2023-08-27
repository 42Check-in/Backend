package check_in42.backend.conferenceRoom;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDay;
import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDayService;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import check_in42.backend.myCheckIn.MyCheckInService;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conference-rooms/")
public class ConferenceController {
    private final ConferenceRoomService conferenceRoomService;
    private final ConferenceCheckDayService conferenceCheckDayService;

    private final UserService userService;

    @GetMapping("calendar/{year}/{month}")
    public ResponseEntity<Long> calender(@PathVariable(name = "year") final Long year,
                                         @PathVariable(name = "month") final Long month) {
        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayService.findByYearMonth(year, month);

        if (conferenceCheckDay == null)
            return ResponseEntity.ok().body(0L);
        return ResponseEntity.ok().body(conferenceCheckDay.getDays());
    }

    @GetMapping("place-time/{date}")
    public ResponseEntity<Map<String, long[]>> placeTime(@PathVariable(name = "date") final LocalDate date) {
        Map<String, long[]> result = conferenceRoomService.makeBase();

        conferenceRoomService.setReservedInfo(result, date);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("form")
    public ResponseEntity inputForm(@RequestBody final ConferenceRoomDTO conferenceRoomDTO,
                                    @UserId final UserInfo userInfo) {
        if (!conferenceRoomService.isInputForm(conferenceRoomDTO))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        conferenceRoomService.inputForm(conferenceRoomDTO, userInfo);

        if (conferenceRoomService.isDayFull(conferenceRoomDTO.getDate()))
            conferenceCheckDayService.updateDenyCheckDay(conferenceRoomDTO.getDate());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "cancel", consumes = {"text/plain", "application/*", MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity cancelForm(@UserId UserInfo userInfo,
                                     @RequestBody Long formId) {
        User user = userService.findByName(userInfo.getIntraId()).orElseThrow(UserRunTimeException.NoUserException::new);
        ConferenceRoom conferenceRoom = conferenceRoomService.findOne(formId);

        conferenceCheckDayService.updateAllowCheckDay(conferenceRoom.getDate());
        conferenceRoomService.deleteById(user, formId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}