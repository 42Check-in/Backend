package check_in42.backend.conferenceRoom.ConferenceRoom;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ConferenceRoomDTO {
    private Long formId;
    private String intraId;
    private LocalDate date;
    private Long reservationCount;
    private Long reservationInfo;

    public static ConferenceRoomDTO create(ConferenceRoom conferenceRoom) {
        ConferenceRoomDTO conferenceRoomDTO = new ConferenceRoomDTO();
        conferenceRoomDTO.id = conferenceRoom.getId();
        conferenceRoomDTO.intraId = conferenceRoom.getUser().getIntraId();
        conferenceRoomDTO.date = conferenceRoom.getDate();
        conferenceRoomDTO.reservationCount = conferenceRoom.getReservationCount();
        conferenceRoomDTO.reservationInfo = conferenceRoom.getReservationInfo();
        return conferenceRoomDTO;
    }
}
