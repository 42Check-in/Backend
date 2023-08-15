package check_in42.backend.visitor;

import check_in42.backend.user.User;
import check_in42.backend.visitor.visitUtils.PriorApproval;
import check_in42.backend.visitor.visitUtils.VisitorsDTO;

public class VisitorsService {

    public void applyVisitorForm(User user, VisitorsDTO visitorsDTO) {
        Visitors visitors = Visitors.builder()
                .priorApproval(new PriorApproval(visitorsDTO))
                .user(user).build();

    }
}
