package check_in42.backend.myCheckIn;

import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.equipments.Equipment;
import check_in42.backend.equipments.EquipmentDTO;
import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.presentation.utils.PresentationDTO;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
import check_in42.backend.visitors.Visitors;
import check_in42.backend.visitors.visitUtils.VisitorsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyCheckInService {

    private final UserService userService;
    public EquipmentDTO findEquipFormFromUser(User user, NoticeDTO noticeDTO) {

        Equipment equip = user.getEquipments().stream()
                .filter(equipment -> equipment.getId().equals(noticeDTO.getFormId()))
                .findFirst().orElse(null);
        if (equip != null)
            return EquipmentDTO.create(equip);

        return null;
    }

    public PresentationDTO findPresentationFormFromUser(User user, NoticeDTO noticeDTO) {

        Presentation presen = user.getPresentations().stream()
                .filter(presentation -> presentation.getId().equals(noticeDTO.getFormId()))
                .findFirst().orElse(null);
        if (presen != null)
            return PresentationDTO.create(presen);

        return null;
    }

    public VisitorsDTO findVisitorFormFromUser(User user, NoticeDTO noticeDTO) {

        Visitors visit = user.getVisitors().stream()
                .filter(visitors -> visitors.getId().equals(noticeDTO.getFormId()))
                .findFirst().orElse(null);
        if (visit != null)
            return VisitorsDTO.create(visit);
        return null;
    }

    public ConferenceRoomDTO findConferenceFormFromUser(User user, Long formId) {

        ConferenceRoom conference = user.getConferenceRooms().stream()
                .filter(conferenceRoom -> conferenceRoom.getId().equals(formId))
                .findFirst().orElse(null);
        if (conference != null)
            return ConferenceRoomDTO.create(conference);
        return null;
    }

    public List<EquipmentDTO> findEquipmentsList(final String intraId) {
        final User user = userService.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Equipment> equipmentList = user.getEquipments();
        final List<EquipmentDTO> result = equipmentList.stream()
                .map(EquipmentDTO::create).collect(Collectors.toList());
        return result;
    }

    public List<PresentationDTO> findPresentationList(final String intraId) {
        final User user = userService.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Presentation> presentationList = user.getPresentations();
        final List<PresentationDTO> result = presentationList.stream()
                .map(PresentationDTO::create).collect(Collectors.toList());
        return result;
    }

    public List<VisitorsDTO> findVisitorsList(final String intraId) {
        final User user = userService.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Visitors> visitorsList = user.getVisitors();
        final List<VisitorsDTO> result = visitorsList.stream()
                .map(VisitorsDTO::create).collect(Collectors.toList());
        return result;
    }

    public List<ConferenceRoomDTO> finaConferenceRoomList(final String intraId) {
        final User user = userService.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<ConferenceRoom> conferenceRoomList = user.getConferenceRooms();
        final List<ConferenceRoomDTO> result = conferenceRoomList.stream()
                .map(ConferenceRoomDTO::create).collect(Collectors.toList());
        return result;
    }
}
