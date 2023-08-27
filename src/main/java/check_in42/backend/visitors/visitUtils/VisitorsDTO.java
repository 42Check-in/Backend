package check_in42.backend.visitors.visitUtils;

import check_in42.backend.visitors.Visitors;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class VisitorsDTO {

    private Long visitorsId;

    private String intraId;

    private String visitorsName;

    private LocalDate visitDate;

    private String visitTime;

    private int visitPurpose;

    private int relationWithUser;

    private int visitPlace;

    private String etcPurpose;

    private String etcRelation;

    private String etcPlace;

    private boolean agreement;

    public static VisitorsDTO create(Visitors visitors) {
        VisitorsDTO visitorsDTO = new VisitorsDTO();
        visitorsDTO.visitorsId = visitors.getId();
        visitorsDTO.intraId = visitors.getPriorApproval().getIntraId();
        visitorsDTO.visitorsName = visitors.getPriorApproval().getVisitorsName();
        visitorsDTO.visitDate = LocalDate.parse(visitors.getPriorApproval().getVisitDate(),
                DateTimeFormatter.ISO_LOCAL_DATE);
        visitorsDTO.visitTime = visitors.getPriorApproval().getVisitTime();
        try {
            visitorsDTO.visitPurpose = VisitPurpose.valueOf(visitors.getPriorApproval()
                    .getVisitPurpose()).ordinal();
        } catch (IllegalArgumentException e) {
            visitorsDTO.visitPurpose = 0;
        }
        try {
            visitorsDTO.relationWithUser = RelationWithUser.valueOf(visitors.getPriorApproval()
                    .getRelationWithUser()).ordinal();
        } catch (IllegalArgumentException e) {
            visitorsDTO.relationWithUser = 0;
        }
        try {
            visitorsDTO.visitPlace = VisitPlace.valueOf(visitors.getPriorApproval().getVisitPlace()).ordinal();
        } catch (IllegalArgumentException e) {
            visitorsDTO.visitPlace = 0;
        }
        visitorsDTO.etcPurpose = visitors.getPriorApproval().getVisitPurpose();
        visitorsDTO.etcRelation = visitors.getPriorApproval().getRelationWithUser();
        visitorsDTO.etcPlace = visitors.getPriorApproval().getVisitPlace();
        visitorsDTO.agreement = visitors.getPriorApproval().isAgreement();

        return visitorsDTO;
    }
}
