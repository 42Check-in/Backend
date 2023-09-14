package check_in42.backend.visitors.visitUtils;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Embeddable
@Getter
@NoArgsConstructor
public class PriorApproval {
    private String intraId;

    private String visitorsName;

    private String visitDate;

    private String visitTime;

    private String visitPurpose;

    private String relationWithUser;

    private String visitPlace;

    private boolean agreement;

    public PriorApproval(VisitorsDTO visitorsDTO) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.intraId = visitorsDTO.getIntraId();
        this.visitorsName = visitorsDTO.getVisitorsName();
        this.visitDate = visitorsDTO.getDate().format(dateTimeFormatter);
        this.visitTime = visitorsDTO.getVisitTime();
        this.visitPurpose = visitorsDTO.getEtcPurpose() != null ?
                visitorsDTO.getEtcPurpose() : VisitPurpose.values()[visitorsDTO.getVisitPurpose()].getName();
        this.relationWithUser = visitorsDTO.getEtcRelation() != null ?
                visitorsDTO.getEtcRelation() : RelationWithUser.values()[visitorsDTO.getRelationWithUser()].getName();
        this.visitPlace = visitorsDTO.getEtcPlace() != null ?
                        visitorsDTO.getEtcPlace() : VisitPlace.values()[visitorsDTO.getVisitPlace()].getName();
        this.agreement = visitorsDTO.isAgreement();
    }

    public void setIntraId(String intraId) {
        this.intraId = intraId;
    }
}
