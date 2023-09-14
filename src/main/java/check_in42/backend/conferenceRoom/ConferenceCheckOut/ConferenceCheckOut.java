package check_in42.backend.conferenceRoom.ConferenceCheckOut;

import check_in42.backend.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ConferenceCheckOut {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDate date;

    private LocalDateTime checkOutDateTime;

    private Long reservationInfo;

    @Builder
    protected ConferenceCheckOut(Long id, User user, LocalDate date, LocalDateTime checkOutDateTime, Long reservationInfo) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.checkOutDateTime = checkOutDateTime;
        this.reservationInfo = reservationInfo;
    }
}
