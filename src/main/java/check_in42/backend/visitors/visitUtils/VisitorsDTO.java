package check_in42.backend.visitors.visitUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VisitorsDTO {

    private Long visitorsId;

    private String intraId;

    private String visitorsName;

    private String visitDate;

    private String visitTime;

    private VisitPurpose visitPurpose;

    private RelationWithUser relationWithUser;

    private VisitPlace visitPlace;

    private boolean agreement;

}
