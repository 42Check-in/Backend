package check_in42.backend.myCheckIn;

import check_in42.backend.conferenceRoom.ConferenceRoom;
import check_in42.backend.equipments.Equipment;
import check_in42.backend.equipments.EquipmentDTO;
import check_in42.backend.user.User;
import check_in42.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyCheckInService {

    private final UserRepository userRepository;

    public Object findFormByCategory(MyCheckInType category, String intraId, Long formId) {
        User user = userRepository.findByName(intraId);

        switch (category) {
            case ETC:
                ConferenceRoom room = user.getConferenceRooms().stream()
                        .filter(conferenceRoom -> conferenceRoom.getId().equals(formId))
                        .findFirst().orElse(null);
                if (room != null)
                    return DTOMake(room);
                break;
            case EQUIPMENT:
                Equipment equip = user.getEquipments().stream()
                        .filter(equipment -> equipment.getId().equals(formId))
                        .findFirst().orElse(null);
                if (equip != null)
                    return EquipmentDTO.create(equip);
        }
    }
}
