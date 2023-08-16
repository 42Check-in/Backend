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

    @Builder
    protected ConferenceRoomDTO(Long id, User user, LocalDate date, Long reservationCount, Long reservationInfo) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.reservationCount = reservationCount;
        this.reservationInfo = reservationInfo;
    }
}
