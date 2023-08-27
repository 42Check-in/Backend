package check_in42.backend.visitors;

import check_in42.backend.user.User;
import check_in42.backend.user.UserRepository;
import check_in42.backend.user.exception.UserRunTimeException;
import check_in42.backend.visitors.visitUtils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitorsService {

    private final VisitorsRepository visitorsRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long applyVisitorForm(String intraId, VisitorsDTO visitorsDTO) {
        final User user = userRepository.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final Visitors visitors = this.createVisitors(user, visitorsDTO);
        visitors.getPriorApproval().setIntraId(intraId);
        visitorsRepository.save(visitors);
        user.addVisitorsForm(visitors);
        return visitors.getId();
    }

    public Visitors createVisitors(User user, VisitorsDTO visitorsDTO) {
        return Visitors.builder()
                .user(user)
                .priorApproval(new PriorApproval(visitorsDTO))
                .build();
    }

    public Optional<Visitors> findById(Long visitorsId) {
        Optional<Visitors> visitors = visitorsRepository.findById(visitorsId);
        return visitors;
    }

    @Transactional
    public void delete(VisitorsDTO visitorsDTO, String intraId) {
        Optional<Visitors> visitors = visitorsRepository.findById(visitorsDTO.getFormId());
        if (visitors.isEmpty())
            return ;
        visitorsRepository.delete(visitors.get());
        User user = userRepository.findByName(intraId).get();
        user.deleteVisitorsForm(visitors.get().getId());
    }

    public List<Visitors> findAll() {
        return visitorsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public void vocalConfirm(List<Long> formId) {
        formId.stream().map(visitorsRepository::findById).forEach(visitors -> visitors.get().vocalConfirm());
    }

    public List<Visitors> findByApproval() {
        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
        List<Visitors> visitorsList = visitorsRepository.findApprovalList(threeDaysAgo);
        return visitorsList;
    }

    public List<Visitors> findByNoticeFalse() {
        return visitorsRepository.findByNoticeFalse();
    }

}
