package check_in42.backend.visitors.visitUtils;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Embeddable
@NoArgsConstructor
@Getter
public class PriorApproval {
    private String intraId;

    private String visitorsName;

    private String visitDate;

    private String visitTime;

    @Enumerated(EnumType.STRING)
    private VisitPurpose visitPurpose;

    @Enumerated(EnumType.STRING)
    private RelationWithUser relationWithUser;

    @Enumerated(EnumType.STRING)
    private VisitPlace visitPlace;

    private boolean agreement;

    public PriorApproval(VisitorsDTO visitorsDTO) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        this.intraId = visitorsDTO.getIntraId();
        this.visitorsName = visitorsDTO.getVisitorsName();
        this.visitDate = simpleDateFormat.format(visitorsDTO.getVisitDate());
        this.visitTime = visitorsDTO.getVisitTime();
        this.visitPurpose = visitorsDTO.getVisitPurpose();
        this.relationWithUser = visitorsDTO.getRelationWithUser();
        this.visitPlace = visitorsDTO.getVisitPlace();
        this.agreement = visitorsDTO.isAgreement();
    }

}
