package check_in42.backend.conferenceRoom.ConferenceRoom;

import check_in42.backend.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ConferenceRoomDTO {
    private Long id;
    private User user;
    private LocalDate date;
    private Long reservationCount;
    private Long reservationInfo;

    public static ConferenceRoomDTO create(ConferenceRoom conferenceRoom) {
        ConferenceRoomDTO conferenceRoomDTO = new ConferenceRoomDTO();
        conferenceRoomDTO.id = conferenceRoom.getId();
        conferenceRoomDTO.user = conferenceRoom.getUser();
        conferenceRoomDTO.date = conferenceRoom.getDate();
        conferenceRoomDTO.reservationCount = conferenceRoom.getReservationCount();
        conferenceRoomDTO.reservationInfo = conferenceRoom.getReservationInfo();
        return conferenceRoomDTO;
    }
}
