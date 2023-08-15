package check_in42.backend.visitor.visitUtils;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class VisitorsDTO {
    private String intraId;

    private String visitorsName;

    private String visitDate;

    private String visitTime;

    private VisitPurpose visitPurpose;

    private RelationWithUser relationWithUser;

    private VisitPlace visitPlace;

    private boolean agreement;

}
