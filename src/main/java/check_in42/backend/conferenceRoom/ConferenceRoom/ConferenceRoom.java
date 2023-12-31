package check_in42.backend.conferenceRoom.ConferenceRoom;

import check_in42.backend.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor
public class ConferenceRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDate date;

    private Long reservationCount;

    private Long reservationInfo; // 클러스터 2Bit, 방 6Bit, 시간 24Bit

    private LocalDateTime checkInTime;

    @Builder
    protected ConferenceRoom(Long id, User user, LocalDate date, Long reservationCount, Long reservationInfo) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.reservationCount = reservationCount;
        this.reservationInfo = reservationInfo;
        this.checkInTime = null;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }
}
