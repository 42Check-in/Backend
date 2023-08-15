package check_in42.backend.conferenceRoom;

import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDay;
import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDayService;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
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

    // 불가능한 날짜 정보
    @GetMapping("calender/{year}/{month}")
    public ResponseEntity<Long> calender(@PathVariable(name = "year")Long year, @PathVariable(name = "month")Long month) {
        Long baseDateBit;

        baseDateBit = ConferenceUtil.getDayBit(year, month);
        ConferenceCheckDay conferenceCheckDay = conferenceCheckDayService.findByDate(year, month);
        if (conferenceCheckDay != null)
            return ResponseEntity.ok().body(baseDateBit);
        return ResponseEntity.ok().body(baseDateBit ^ conferenceCheckDay.getFullDay());
    }

    @GetMapping("place-time/{day}")
    public ResponseEntity<Map<String, Long[]>> placeTime(@PathVariable(name = "day")Long day) {
        Map<String, Long[]> result = new HashMap<>();
        PlaceInfo[] placeInfos = PlaceInfo.values();
        RoomCount[] roomInfos = RoomCount.values();
        Long cluster, room, time;

        for (int i = 0; i < placeInfos.length; i++) {
            result.put(placeInfos[i].getValue(), new Long[roomInfos[i].getValue()]);
            Arrays.fill(result.get(placeInfos[i].getValue()), PlaceInfoBit.TIME.getValue());
        }

        cluster = room = time = 0L;
        List<ConferenceRoom> conferenceRooms = conferenceRoomService.findByDay(day);
        for (ConferenceRoom cr: conferenceRooms) {
            cluster = cr.getReservationInfo() >> (PlaceInfoBitSize.ROOM.getValue() + PlaceInfoBitSize.TIME.getValue());
            room = (cr.getReservationInfo() >> PlaceInfoBitSize.TIME.getValue()) & PlaceInfoBit.ROOM.getValue();
            time = cr.getReservationInfo() & PlaceInfoBit.TIME.getValue();
            Long[] rooms = ConferenceUtil.getCluster(result, ConferenceUtil.BitIdx(cluster));
            rooms[ConferenceUtil.BitIdx(room)] ^= time;
        }
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("form")
    public ResponseEntity inputForm(@RequestBody ConferenceRoomDTO conferenceRoomDTO, @CookieValue(name = "intraId") String intraId) {

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
