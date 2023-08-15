package check_in42.backend.visitors.visitUtils;

import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class VisitorsDTO {

    private Long visitorsId;

    private String intraId;

    private String visitorsName;

    private Date visitDate;

    private String visitTime;

    private int visitPurpose;

    private int relationWithUser;

    private int visitPlace;

    private boolean agreement;

}
