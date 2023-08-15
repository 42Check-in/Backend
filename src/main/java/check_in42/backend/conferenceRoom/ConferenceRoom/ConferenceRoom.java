package check_in42.backend.conferenceRoom.ConferenceRoom;

import check_in42.backend.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Getter
public class ConferenceRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDate date;

    private Long reservationCount;

    private Long reservationInfo; // 클러스터 2Bit, 방 6Bit, 시간 24Bit

    @Builder
    protected ConferenceRoom(Long id, User user, LocalDate date, Long reservationCount, Long reservationInfo) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.reservationCount = reservationCount;
        this.reservationInfo = reservationInfo;
    }
}
