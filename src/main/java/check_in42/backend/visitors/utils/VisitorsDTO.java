package check_in42.backend.visitors.utils;

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

    private String etcPurpose;

    private String etcRelation;

    private String etcPlace;

    private boolean agreement;
}
