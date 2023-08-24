package check_in42.backend.conferenceRoom.ConferenceCheckDay;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor
public class ConferenceCheckDay {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String year;

    private String month;

    private Long days;

    @Builder
    protected ConferenceCheckDay(Long id, String year, String month, Long days) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.days = days;
    }

    public void setDays(Long days) {
        this.days = days;
    }
}
