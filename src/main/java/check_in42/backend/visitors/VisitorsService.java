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
import java.util.stream.Collectors;

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
    public void delete(Long formId, String intraId) {
        Optional<Visitors> visitors = visitorsRepository.findById(formId);
        if (visitors.isEmpty())
            return ;
        visitorsRepository.delete(visitors.get());
        User user = userRepository.findByName(intraId).get();
        user.deleteVisitorsForm(visitors.get().getId());
    }

    public List<VisitorsDTO> findAll() {
        final List<Visitors> visitorsList = visitorsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        final List<VisitorsDTO> result = visitorsList.stream()
                .map(VisitorsDTO::create)
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public void vocalConfirm(List<Long> formIds) {
        formIds.stream().map(formId -> visitorsRepository.findById(formId)
                .orElseThrow(UserRunTimeException.NoUserException::new)).forEach(Visitors::vocalConfirm);
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
