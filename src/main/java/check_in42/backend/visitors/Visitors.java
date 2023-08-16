package check_in42.backend.visitors;

import check_in42.backend.user.User;
import check_in42.backend.visitors.visitUtils.PriorApproval;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class Visitors {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PriorApproval priorApproval;

    private LocalDate approval;

    @Builder.Default
    private boolean notice = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    protected Visitors(User user, PriorApproval priorApproval) {
        this.user = user;
        this.priorApproval = priorApproval;
        this.approval = null;
    }
    public void vocalConfirm() {
        this.approval = LocalDate.now();
    }
}
