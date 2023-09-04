package check_in42.backend.tablet;

import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class TabletDTO {

    private List<ConferenceRoomDTO> conferenceRoomDTOList;

    TabletDTO(List<ConferenceRoomDTO> conferenceRoomDTOList) {
        this.conferenceRoomDTOList = conferenceRoomDTOList;
    }
}
