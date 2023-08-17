package check_in42.backend.visitors.visitUtils;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Embeddable
@Getter
@NoArgsConstructor
public class PriorApproval {
    private String intraId;

    private String visitorsName;

    private String visitDate;

    private String visitTime;

//    @Enumerated(EnumType.STRING)
    private String visitPurpose;

//    @Enumerated(EnumType.STRING)
    private String relationWithUser;

//    @Enumerated(EnumType.STRING)
    private String visitPlace;

    private boolean agreement;

    private boolean notice;


    public PriorApproval(VisitorsDTO visitorsDTO) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.intraId = visitorsDTO.getIntraId();
        this.visitorsName = visitorsDTO.getVisitorsName();
        this.visitDate = visitorsDTO.getVisitDate().format(dateTimeFormatter);
        this.visitTime = visitorsDTO.getVisitTime();
        this.visitPurpose = VisitPurpose.values()[visitorsDTO.getVisitPurpose()].getPurposeType();
        this.relationWithUser = RelationWithUser.values()[visitorsDTO.getRelationWithUser()].getType();
        this.visitPlace = VisitPlace.values()[visitorsDTO.getVisitPlace()].getPlaceType();
        this.agreement = visitorsDTO.isAgreement();
        this.notice = false;
    }

}
