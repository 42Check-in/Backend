package check_in42.backend.tablet;

import check_in42.backend.conferenceRoom.ConferenceCheckDay.ConferenceCheckDayService;
import check_in42.backend.conferenceRoom.ConferenceCheckOut.ConferenceCheckOutService;
import check_in42.backend.conferenceRoom.ConferenceEnum.PlaceInfoBit;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
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
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tablet/")
@Slf4j
public class TabletController {

    private final ConferenceCheckOutService conferenceCheckOutService;
    private final ConferenceRoomService conferenceRoomService;
    private final TabletService tabletService;

    @GetMapping("reservations/{place}")
    public ResponseEntity<TabletDTO> reservations(@PathVariable(name = "place") final String roomName) {
        TabletDTO tabletDTO = new TabletDTO(tabletService
                .findAllByPlaceAndNowOver(LocalDate.now(), Rooms.valueOf(roomName).getRoomBit(), ConferenceUtil.getAfterNowBit()));
        return ResponseEntity.ok(tabletDTO);
    }

    @PostMapping("check-in")
    public ResponseEntity updateState(@RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        tabletService.updateState(conferenceRoomDTO.getFormId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    ConferenceCheckDayService conferenceCheckDayService;

    @PostMapping("check-out")
    public ResponseEntity checkOut(@RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        conferenceCheckOutService.inputCheckOut(conferenceRoomDTO.getFormId());

        log.info("check-out 여기까지 오냐?");
        conferenceCheckDayService.updateAllowCheckDay(conferenceRoomService.findOne(conferenceRoomDTO.getFormId()));

        log.info("여기까지는????");
        conferenceRoomService.cancelForm(conferenceRoomDTO, conferenceRoomDTO.getIntraId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("cancel")
    public ResponseEntity deleteForm(@RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        conferenceCheckDayService.updateAllowCheckDay(conferenceRoomService.findOne(conferenceRoomDTO.getFormId()));
        conferenceRoomService.cancelForm(conferenceRoomDTO, conferenceRoomDTO.getIntraId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
