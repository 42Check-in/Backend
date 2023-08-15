package check_in42.backend.visitors;

import check_in42.backend.user.User;
import check_in42.backend.user.UserRepository;
import check_in42.backend.visitors.visitUtils.PriorApproval;
import check_in42.backend.visitors.visitUtils.VisitorsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitorsService {

    private final VisitorsRepository visitorsRepository;
    private final UserRepository userRepository;

    public void applyVisitorForm(User user, VisitorsDTO visitorsDTO) {
        Visitors visitors = Visitors.builder()
                .priorApproval(new PriorApproval(visitorsDTO))
                .confirm(false)
                .user(user).build();
        visitorsRepository.save(visitors);
        user.addVisitorsForm(visitors);
    }

    public Optional<Visitors> findById(Long visitorsId) {
        Optional<Visitors> visitors = visitorsRepository.findById(visitorsId);
        return visitors;
    }

    public void delete(Optional<Visitors> visitors, String intraId) {
        if (visitors.isEmpty())
            return ;
        visitorsRepository.delete(visitors.get());
        User user = userRepository.findByName(intraId);
        user.deleteVisitorsForm(visitors.get().getId());
    }
}
