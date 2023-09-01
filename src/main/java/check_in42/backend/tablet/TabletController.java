package check_in42.backend.tablet;

import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tablet/")
public class TabletController {

    private final ConferenceRoomService conferenceRoomService;

    @GetMapping("reservations")
    public ResponseEntity<List<ConferenceRoomDTO>> reservations() {
        return ResponseEntity.ok(conferenceRoomService.findByDateConferenceRooms(LocalDate.parse("2023-09-01")));
    }
}
