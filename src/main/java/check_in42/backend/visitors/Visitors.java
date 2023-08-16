package check_in42.backend.visitors;

import check_in42.backend.user.User;
import check_in42.backend.visitors.visitUtils.PriorApproval;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class Visitors {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PriorApproval priorApproval;

    @Builder.Default
    private boolean confirm = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    protected Visitors(User user, PriorApproval priorApproval) {
        this.user = user;
        this.priorApproval = priorApproval;
    }
    public void vocalConfirm() {
        this.confirm = true;
    }
}
