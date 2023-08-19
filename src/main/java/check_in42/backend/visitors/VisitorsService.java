package check_in42.backend.visitors;

import check_in42.backend.user.User;
import check_in42.backend.user.UserRepository;
import check_in42.backend.visitors.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitorsService {

    private final VisitorsRepository visitorsRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long applyVisitorForm(User user, Visitors visitors) {
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
        Optional<Visitors> visitors = visitorsRepository.findById(visitorsDTO.getVisitorsId());
        if (visitors.isEmpty())
            return ;
        visitorsRepository.delete(visitors.get());
        User user = userRepository.findByName(intraId);
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

    public Visitors findByUserAndFormId(User user, Long id) {
        final Visitors visitors = findByUserAndFormId(user, id);
        return visitors;
    }

    public VisitorsDTO visitorsToDto(User user, Long id) {
        final Visitors visitors = findByUserAndFormId(user, id);
        VisitorsDTO visitorsDTO = VisitorsDTO.builder()
                .visitorsId(visitors.getId())
                .intraId(visitors.getPriorApproval().getIntraId())
                .visitorsName(visitors.getPriorApproval().getVisitorsName())
                .visitDate(LocalDate.parse(visitors.getPriorApproval().getVisitDate(), DateTimeFormatter.ISO_DATE))
                .visitTime(visitors.getPriorApproval().getVisitTime())
                .visitPurpose(VisitPurpose.valueOf(visitors.getPriorApproval().getVisitPurpose()).ordinal())
                .relationWithUser(RelationWithUser.valueOf(visitors.getPriorApproval().getRelationWithUser()).ordinal())
                .visitPlace(VisitPlace.valueOf(visitors.getPriorApproval().getVisitPlace()).ordinal())
                .etcPurpose(visitors.getPriorApproval().getVisitPurpose())
                .etcPlace(visitors.getPriorApproval().getVisitPlace())
                .etcRelation(visitors.getPriorApproval().getRelationWithUser())
                .agreement(visitors.getPriorApproval().isAgreement())
                .build();
        return visitorsDTO;
    }
}
