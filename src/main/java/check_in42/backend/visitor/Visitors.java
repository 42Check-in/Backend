package check_in42.backend.visitor;

import check_in42.backend.user.User;
import check_in42.backend.visitor.visitUtils.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
