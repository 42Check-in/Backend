package check_in42.backend.tablet;

import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfoBit;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import check_in42.backend.conferenceRoom.ConferenceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tablet/")
public class TabletController {

    private final ConferenceRoomService conferenceRoomService;

    @GetMapping("reservations/{place}")
    public ResponseEntity<TabletDTO> reservations(@PathVariable(name = "place") final String roomName) {
        int nowTimeIdx = TabletUtil.getTimeIdx();

        long timeBit = 0;
        for (int i = 0; i < nowTimeIdx; i++) {
            timeBit = (timeBit << 1) | 1;
        }
        timeBit = PlaceInfoBit.TIME.getValue() & ~timeBit;

        TabletDTO tabletDTO = new TabletDTO(conferenceRoomService
                .findAllByPlaceAndNowOver(LocalDate.now(), Rooms.valueOf(roomName).getRoomBit(), timeBit));
        return ResponseEntity.ok(tabletDTO);
    }
}
