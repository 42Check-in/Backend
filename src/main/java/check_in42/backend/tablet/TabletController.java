package check_in42.backend.tablet;

import check_in42.backend.conferenceRoom.ConferenceCheckOut.ConferenceCheckOutService;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import check_in42.backend.conferenceRoom.ConferenceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tablet/")
public class TabletController {

    private final ConferenceCheckOutService conferenceCheckOutService;
    private final ConferenceRoomService conferenceRoomService;
    private final TabletService tabletService;

    @GetMapping("reservations/{place}")
    public ResponseEntity<TabletDTO> reservations(@PathVariable(name = "place") final String roomName) {
        TabletDTO tabletDTO = new TabletDTO(tabletService
                .findAllByPlaceAndNowOver(LocalDate.now(), Rooms.valueOf(roomName).getRoomBit(), ConferenceUtil.getAfterTimeBit(ConferenceUtil.getTimeIdx(LocalDateTime.now().withDayOfMonth(15).withHour(10).withMinute(30)))));
        return ResponseEntity.ok(tabletDTO);
    }

    @PostMapping("check-in")
    public ResponseEntity updateState(@RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        tabletService.updateState(conferenceRoomDTO.getFormId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("check-out")
    public ResponseEntity checkOut(@RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        conferenceCheckOutService.inputCheckOut(conferenceRoomDTO.getFormId());
        conferenceRoomService.deleteForm(conferenceRoomDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("cancel")
    public ResponseEntity deleteForm(@RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        conferenceRoomService.deleteForm(conferenceRoomDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
