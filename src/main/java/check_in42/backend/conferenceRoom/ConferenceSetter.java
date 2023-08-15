package check_in42.backend.conferenceRoom;

import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;

public class ConferenceSetter {
    public static ConferenceRoom conferenceRoomBuilder(ConferenceRoomDTO conferenceRoomDTO) {
        return ConferenceRoom.builder()
                .id(conferenceRoomDTO.getId())
                .user(conferenceRoomDTO.getUser())
                .date(conferenceRoomDTO.getDate())
                .reservationCount(conferenceRoomDTO.getReservationCount())
                .reservationInfo(conferenceRoomDTO.getReservationInfo())
                .build();
    }
}
