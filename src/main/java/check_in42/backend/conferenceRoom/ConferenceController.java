package check_in42.backend.conferenceRoom;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDay;
import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDayService;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conference-rooms/")
@Slf4j
public class ConferenceController {
    private final ConferenceRoomService conferenceRoomService;
    private final ConferenceCheckDayService conferenceCheckDayService;

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
        log.info("요청 들어오니?");
        Map<String, long[]> result = conferenceRoomService.makeBase();

        conferenceRoomService.setReservedInfo(result, date);
        log.info("혹시 이전에 끝나니?");
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

    @PostMapping("cancel")
    public ResponseEntity cancelForm(@RequestBody final ConferenceRoomDTO conferenceRoomDTO,
                                     @UserId final UserInfo userInfo) {
        conferenceCheckDayService.updateAllowCheckDay(conferenceRoomService.findOne(conferenceRoomDTO.getFormId()));
        conferenceRoomService.cancelForm(conferenceRoomDTO, userInfo);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}