package check_in42.backend.presentation.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponsePresentation {

    private List<PresentationDTO> list;
    private int pageCount;


    public static ResponsePresentation create(List<PresentationDTO> presentations, int count) {
        ResponsePresentation responsePresentation = new ResponsePresentation();
        responsePresentation.list = presentations;
        responsePresentation.pageCount = count;

        return responsePresentation;
    }
}
