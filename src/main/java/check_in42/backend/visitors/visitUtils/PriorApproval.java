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
        this.visitDate = visitorsDTO.getVisitDate().format(dateTimeFormatter);
        this.visitTime = visitorsDTO.getVisitTime();
        this.visitPurpose = visitorsDTO.getEtcPurpose() != null ? VisitPurpose.values()[visitorsDTO.getVisitPurpose()].getPurposeType()
                + visitorsDTO.getEtcPurpose() : VisitPurpose.values()[visitorsDTO.getVisitPurpose()].getPurposeType();
        this.relationWithUser = visitorsDTO.getEtcRelation() != null ? RelationWithUser.values()[visitorsDTO.getRelationWithUser()].getType()
                + visitorsDTO.getEtcRelation() : RelationWithUser.values()[visitorsDTO.getRelationWithUser()].getType();
        this.visitPlace = visitorsDTO.getEtcPlace() != null ? VisitPlace.values()[visitorsDTO.getVisitPlace()].getPlaceType()
                + visitorsDTO.getEtcPlace() : VisitPlace.values()[visitorsDTO.getVisitPlace()].getPlaceType();
        this.agreement = visitorsDTO.isAgreement();
    }

}
