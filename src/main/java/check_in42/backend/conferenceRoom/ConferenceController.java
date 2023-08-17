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
    private final static Long reservationAllTimeNum = RoomCount.GAEPO.getValue() * 24 + RoomCount.SEOCHO.getValue() * 24;

    // 불가능한 날짜 정보
    @GetMapping("calender/{year}/{month}")
    public ResponseEntity<Long> calender(@PathVariable(name = "year") Long year, @PathVariable(name = "month") Long month) {
        Long baseDateBit = ConferenceUtil.getDayBit(year, month);

        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayService.findByDate(year, month);
        if (conferenceCheckDay != null)
            return ResponseEntity.ok().body(baseDateBit);
        return ResponseEntity.ok().body(conferenceCheckDay.getDays());
    }

    @GetMapping("place-time/{day}")
    public ResponseEntity<Map<String, Long[]>> placeTime(@PathVariable(name = "day") Long day) {
        Map<String, Long[]> result = new HashMap<>();
        PlaceInfo[] placeInfos = PlaceInfo.values();
        RoomCount[] roomInfos = RoomCount.values();
        Long[] reservationInfo; // base_length: 3, cluster: 0, room: 1, time: 2;

        for (int i = 0; i < placeInfos.length; i++) {
            result.put(placeInfos[i].getValue(), new Long[roomInfos[i].getValue().intValue()]);
            Arrays.fill(result.get(placeInfos[i].getValue()), PlaceInfoBit.TIME.getValue());
        }

        List<ConferenceRoom> conferenceRooms = conferenceRoomService.findByDay(day);
        for (ConferenceRoom cr : conferenceRooms) {
            reservationInfo = ConferenceUtil.setReservationInfo(cr.getReservationInfo());
            Long[] rooms = ConferenceUtil.getRooms(result, ConferenceUtil.BitIdx(reservationInfo[0]));
            rooms[ConferenceUtil.BitIdx(reservationInfo[1])] ^= reservationInfo[2];
        }
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("form")
    public ResponseEntity inputForm(@RequestBody ConferenceRoomDTO conferenceRoomDTO, @CookieValue(name = "intraId") String intraId) {
        List<ConferenceRoom> reservationList = conferenceRoomService.findByDateAndSamePlace(conferenceRoomDTO.getDate().toString(),
                conferenceRoomDTO.getReservationInfo() >> PlaceInfoBit.TIME.getValue());
        Long[] reservationInfo, reqFormReservationInfo;
        Long emptyTime = PlaceInfoBit.TIME.getValue();

        reqFormReservationInfo = ConferenceUtil.setReservationInfo(conferenceRoomDTO.getReservationInfo());
        for (ConferenceRoom rcr: reservationList) {
            reservationInfo = ConferenceUtil.setReservationInfo(rcr.getReservationInfo());
            emptyTime ^= reservationInfo[2];
        }
        if ((emptyTime & reqFormReservationInfo[2]) != reqFormReservationInfo[2])
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        User user = userService.findByName(intraId);
        ConferenceRoom conferenceRoom = ConferenceRoom.builder()
                .user(user)
                .date(conferenceRoomDTO.getDate())
                .reservationCount(ConferenceUtil.BitN(PlaceInfoBit.TIME.getValue() & conferenceRoomDTO.getReservationInfo()))
                .reservationInfo(conferenceRoomDTO.getReservationInfo())
                .build();
        conferenceRoomService.join(conferenceRoom);
        user.addConferenceForm(conferenceRoom);

        if (conferenceRoomService.getSumReservationCountByDate(conferenceRoomDTO.getDate().toString()) < reservationAllTimeNum) {
            return ResponseEntity.ok(HttpStatus.OK);
        }

        conferenceCheckDayService.updateDenyCheckDay(conferenceRoomDTO.getDate());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("cancel")
    public ResponseEntity cancelForm(@CookieValue("intraId") String intraId, @RequestBody Long formId) {
        User user = userService.findByName(intraId);
        ConferenceRoomDTO conferenceRoomDTO = myCheckInService.findConferenceFormFromUser(user, formId);

        conferenceCheckDayService.updateAllowCheckDay(conferenceRoomDTO.getDate());
        conferenceRoomService.deleteById(formId);
        user.deleteConferenceRoomForm(formId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}