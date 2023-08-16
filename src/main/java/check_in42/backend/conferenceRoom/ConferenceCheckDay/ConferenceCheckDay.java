package check_in42.backend.conferenceRoom.ConferenceCheckDay;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity @Getter
public class ConferenceCheckDay {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long year;

    private Long month;

    private Long days;

    @Builder
    protected ConferenceCheckDay(Long id, Long year, Long month, Long days) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.days = days;
    }

    public void setDays(Long days) {
        this.days = days;
    }
}
