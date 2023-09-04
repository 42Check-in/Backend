package check_in42.backend.tablet;

import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tablet/")
public class TabletController {

    private final ConferenceRoomService conferenceRoomService;

    @GetMapping("reservations/{place}")
    public ResponseEntity<TabletDTO> reservations(@PathVariable(name = "place") final String roomName) {
        TabletDTO tabletDTO = new TabletDTO(conferenceRoomService.findAllByNowAndPlace(LocalDate.now(), Rooms.valueOf(roomName).getRoomBit()));
        return ResponseEntity.ok(tabletDTO);
    }
}
