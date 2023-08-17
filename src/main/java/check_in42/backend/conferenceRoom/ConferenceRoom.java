package check_in42.backend.conferenceRoom;

import check_in42.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class ConferenceRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long location;

    private Long roomInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
