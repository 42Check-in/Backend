package check_in42.backend.notice;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Notice {
    @Id
    private Long id;
    private Long category;
    private Long formId;
    private LocalDateTime approval;
    private boolean notice;
}
