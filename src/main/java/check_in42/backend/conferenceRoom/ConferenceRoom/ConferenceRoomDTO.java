package check_in42.backend.conferenceRoom.ConferenceRoom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class ConferenceRoomDTO {
    private Long formId;
    private Long userId;
    private String intraId;
    private LocalDate date;
    private Long reservationCount;
    private Long reservationInfo;
    private LocalDateTime checkInTime;

    public static ConferenceRoomDTO create(ConferenceRoom conferenceRoom) {
        ConferenceRoomDTO conferenceRoomDTO = new ConferenceRoomDTO();
        conferenceRoomDTO.formId = conferenceRoom.getId();
        conferenceRoomDTO.userId = conferenceRoom.getUser().getId();
        conferenceRoomDTO.intraId = conferenceRoom.getUser().getIntraId();
        conferenceRoomDTO.date = conferenceRoom.getDate();
        conferenceRoomDTO.reservationCount = conferenceRoom.getReservationCount();
        conferenceRoomDTO.reservationInfo = conferenceRoom.getReservationInfo();
        conferenceRoomDTO.checkInTime = conferenceRoom.getCheckInTime();
        return conferenceRoomDTO;
    }
}
