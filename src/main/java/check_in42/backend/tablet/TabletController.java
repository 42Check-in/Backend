package check_in42.backend.tablet;

import check_in42.backend.conferenceRoom.ConferenceCheckOut.ConferenceCheckOutService;
import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfoBit;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import check_in42.backend.conferenceRoom.ConferenceUtil;
import check_in42.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tablet/")
@Slf4j
public class TabletController {

    private final ConferenceCheckOutService conferenceCheckOutService;
    private final TabletService tabletService;
    private final UserService userService;

    @GetMapping("reservations/{place}")
    public ResponseEntity<TabletDTO> reservations(@PathVariable(name = "place") final String roomName) {
        int nowTimeIdx = TabletUtil.getTimeIdx();

        long timeBit = 0;
        for (int i = 0; i < nowTimeIdx; i++) {
            timeBit = (timeBit << 1) | 1;
        }
        timeBit = PlaceInfoBit.TIME.getValue() & ~timeBit;

        TabletDTO tabletDTO = new TabletDTO(tabletService
                .findAllByPlaceAndNowOver(LocalDate.now(), Rooms.valueOf(roomName).getRoomBit(), timeBit));
        return ResponseEntity.ok(tabletDTO);
    }

//    @PostMapping("check-in")
//    public ResponseEntity updateState(@RequestBody ConferenceRoomDTO conferenceRoomDTO) {
//        tabletService.updateState(conferenceRoomDTO.getFormId());
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

    @PostMapping("check-out")
    public ResponseEntity checkOut(@RequestBody final ConferenceRoomDTO conferenceRoomDTO) {
        conferenceCheckOutService.join(conferenceCheckOutService
                .create(conferenceRoomDTO, userService.findOne(conferenceRoomDTO.getUserId())));
        tabletService.deleteForm(conferenceRoomDTO.getFormId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("cancel")
    public ResponseEntity deleteForm(@RequestBody final ConferenceRoomDTO conferenceRoomDTO) {
        log.info("deleteForm 들어옴!!");
        log.info(tabletService.deleteForm(conferenceRoomDTO.getFormId()) + " 삭제!!");
        log.info("deleteForm 종료!!");
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
