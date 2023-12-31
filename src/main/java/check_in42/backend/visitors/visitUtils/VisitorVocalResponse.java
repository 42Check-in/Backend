package check_in42.backend.visitors.visitUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class VisitorVocalResponse {

    private List<VisitorsDTO> list;
    private int pageCount;

    public static VisitorVocalResponse create(final List<VisitorsDTO> visitorsDTOS, int offSet) {
        VisitorVocalResponse visitorVocalResponse = new VisitorVocalResponse();
        visitorVocalResponse.list = visitorsDTOS;
        visitorVocalResponse.pageCount = offSet;

        return visitorVocalResponse;
    }
}
