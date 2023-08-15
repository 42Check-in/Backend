package check_in42.backend.visitors;

import check_in42.backend.user.User;
import check_in42.backend.visitors.visitUtils.*;
import jakarta.persistence.*;
import lombok.*;

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
