package check_in42.backend.visitors.visitUtils;

import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class VisitorsDTO {

    private Long visitorsId;

    private String intraId;

    private String visitorsName;

    private LocalDate visitDate;

    private String visitTime;

    private int visitPurpose;

    private int relationWithUser;

    private int visitPlace;

    private boolean agreement;
}
