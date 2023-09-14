package check_in42.backend.conferenceRoom;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDay;
import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDayService;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
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

    private final UserService userService;
    private final Object lock = new Object();

    @GetMapping("calendar/{year}/{month}")
    public ResponseEntity<Long> calender(@PathVariable(name = "year") final Long year,
                                         @PathVariable(name = "month") final Long month) {
        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayService.findByYearMonth(year, month);
        Long result = 0L;

        if (conferenceCheckDay != null)
            result = conferenceCheckDay.getDays();
        if (conferenceRoomService.isTodayFull())
            result |= 1L << LocalDate.now().getDayOfMonth() - 1;
        log.info("dayBit==> " + result);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("place-time/{date}")
    public ResponseEntity<Map<String, long[][]>> placeTime(@UserId final UserInfo userInfo,
                                                           @PathVariable(name = "date") final LocalDate date) {
        log.info("요청 들어오니?");
        Map<String, long[][]> result = conferenceRoomService.makeBase();

        conferenceRoomService.setReservedInfo(result, userInfo.getIntraId(), date);
        log.info("혹시 이전에 끝나니?");
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("form")
    public ResponseEntity inputForm(@RequestBody final ConferenceRoomDTO conferenceRoomDTO,
                                    @UserId final UserInfo userInfo) {
        synchronized (lock) {
            conferenceRoomDTO.setUserId(userService.findByName(userInfo.getIntraId())
                    .orElseThrow(UserRunTimeException.NoUserException::new).getId());
            conferenceRoomService.isInputForm(conferenceRoomDTO);

            conferenceRoomService.inputForm(conferenceRoomDTO, userInfo);
        }

        if (conferenceRoomService.isDayFull(conferenceRoomDTO.getDate()))
            conferenceCheckDayService.updateDenyCheckDay(conferenceRoomDTO.getDate());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("cancel")
    public ResponseEntity cancelForm(@RequestBody final ConferenceRoomDTO conferenceRoomDTO) {
        conferenceRoomService.deleteForm(conferenceRoomDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}