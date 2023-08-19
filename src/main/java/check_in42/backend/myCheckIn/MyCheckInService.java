package check_in42.backend.myCheckIn;

import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.equipments.Equipment;
import check_in42.backend.equipments.EquipmentDTO;
import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.presentation.utils.PresentationDTO;
import check_in42.backend.user.User;
import check_in42.backend.visitors.Visitors;
import check_in42.backend.visitors.visitUtils.VisitorsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyCheckInService {

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
//        if (visit != null)
//            return VisitorsDTO.create(visit);

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


}
