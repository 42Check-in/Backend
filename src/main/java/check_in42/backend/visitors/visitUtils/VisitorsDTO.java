package check_in42.backend.visitors.visitUtils;

import check_in42.backend.visitors.Visitors;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Slf4j
public class VisitorsDTO {

    private Long formId;

    private String intraId;

    private String visitorsName;

    private LocalDate date;

    private String visitTime;

    private int visitPurpose;

    private int relationWithUser;

    private int visitPlace;

    private String etcPurpose;

    private String etcRelation;

    private String etcPlace;

    private boolean agreement;

    private int status;

    public static VisitorsDTO create(Visitors visitors) {
        VisitorsDTO visitorsDTO = new VisitorsDTO();
        visitorsDTO.formId = visitors.getId();
        visitorsDTO.intraId = visitors.getPriorApproval().getIntraId();
        visitorsDTO.visitorsName = visitors.getPriorApproval().getVisitorsName();
        visitorsDTO.date = LocalDate.parse(visitors.getPriorApproval().getVisitDate(),
                DateTimeFormatter.ISO_LOCAL_DATE);
        visitorsDTO.visitTime = visitors.getPriorApproval().getVisitTime();
        visitorsDTO.visitPurpose = VisitPurpose
                .getOrdinalByDescription(visitors.getPriorApproval().getVisitPurpose());
        visitorsDTO.relationWithUser = RelationWithUser
                .getOrdinalByDescription(visitors.getPriorApproval().getRelationWithUser());
        visitorsDTO.visitPlace = VisitPlace
                .getOrdinalByDescription(visitors.getPriorApproval().getVisitPlace());
        visitorsDTO.etcPurpose = visitorsDTO.getVisitPurpose() == 0
                ? null : visitors.getPriorApproval().getVisitPurpose();
        visitorsDTO.etcRelation = visitorsDTO.getRelationWithUser() == 0
                ? null : visitors.getPriorApproval().getRelationWithUser();
        visitorsDTO.etcPlace = visitorsDTO.getVisitPlace() == 0
                ? null : visitors.getPriorApproval().getVisitPlace();
        visitorsDTO.agreement = visitors.getPriorApproval().isAgreement();
        visitorsDTO.status = visitors.getApproval() != null ? 1 : 0;
        return visitorsDTO;
    }
}
