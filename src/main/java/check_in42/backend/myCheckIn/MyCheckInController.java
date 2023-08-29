package check_in42.backend.myCheckIn;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomService;
import check_in42.backend.equipments.Equipment;
import check_in42.backend.equipments.EquipmentDTO;
import check_in42.backend.equipments.EquipmentService;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.presentation.PresentationService;
import check_in42.backend.presentation.utils.PresentationDTO;
import check_in42.backend.visitors.Visitors;
import check_in42.backend.visitors.VisitorsService;
import check_in42.backend.visitors.visitUtils.VisitorsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-checkin")
@RequiredArgsConstructor
@Slf4j
public class MyCheckInController {

    private final MyCheckInService myCheckInService;
    private final ConferenceRoomService conferenceRoomService;
    private final VisitorsService visitorsService;
    private final PresentationService presentationService;
    private final EquipmentService equipmentService;

    @GetMapping("/conference-rooms")
    public ResponseEntity myConferenceRooms(@UserId final UserInfo userInfo) {
        final List<ConferenceRoomDTO> conferenceRoomList = myCheckInService
                .userConferenceRoomList(userInfo.getIntraId());
        return ResponseEntity.ok(conferenceRoomList);
    }

    @GetMapping("/visitors")
    public ResponseEntity myVisitors(@UserId final UserInfo userInfo) {
        final List<VisitorsDTO> visitorsList = myCheckInService
                .userVisitorsList(userInfo.getIntraId());
        return ResponseEntity.ok(visitorsList);
    }

    @GetMapping("/presentations")
    public ResponseEntity myPresentations(@UserId final UserInfo userInfo) {
        final List<PresentationDTO> presentationList = myCheckInService
                .userPresentationList(userInfo.getIntraId());
        return ResponseEntity.ok(presentationList);
    }

    @GetMapping("/equipments")
    public ResponseEntity myEquipments(@UserId final UserInfo userInfo) {
        final List<EquipmentDTO> equipmentList = myCheckInService
                .userEquipmentsList(userInfo.getIntraId());
        return ResponseEntity.ok(equipmentList);
    }

    @GetMapping("/conference-rooms/{formId}")
    public ResponseEntity conferenceRoomForm(@PathVariable final Long formId) {
        final ConferenceRoom conferenceRoom = conferenceRoomService.findOne(formId);
        return ResponseEntity.ok(ConferenceRoomDTO.create(conferenceRoom));
    }

    @GetMapping("/visitors/{formId}")
    public ResponseEntity visitorForm(@PathVariable final Long formId) {
        final Visitors visitors = visitorsService.findById(formId).get();
        return ResponseEntity.ok(VisitorsDTO.create(visitors));
    }

    @GetMapping("/presentations/{formId}")
    public ResponseEntity presentationForm(@PathVariable final Long formId) {
        final Presentation presentation = presentationService.findOne(formId);
        return ResponseEntity.ok(PresentationDTO.create(presentation));
    }

    @GetMapping("/equipments/{formId}")
    public ResponseEntity equipmentForm(@PathVariable final Long formId) {
        final Equipment equipment = equipmentService.findOne(formId);
        return ResponseEntity.ok(EquipmentDTO.create(equipment));
    }

}
