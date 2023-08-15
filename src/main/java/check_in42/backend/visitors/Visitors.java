package check_in42.backend.visitors;

import check_in42.backend.user.User;
import check_in42.backend.visitors.visitUtils.PriorApproval;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@Builder
public class Visitors {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PriorApproval priorApproval;

    private boolean confirm;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
