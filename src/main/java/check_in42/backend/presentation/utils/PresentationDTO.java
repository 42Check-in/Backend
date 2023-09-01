package check_in42.backend.presentation.utils;

import check_in42.backend.presentation.Presentation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
public class PresentationDTO {

    private Long formId;
    private String userName;
    private String date;
    private String title;
    private String subject;
    private String detail;
    private int time;
    private int type;
    private int status;
    private int screen;
    private List<Long> formIds;
    private String intraId;

    public static PresentationDTO create(Presentation presentation) {
        PresentationDTO presentationDTO = new PresentationDTO();
        presentationDTO.formId = presentation.getId();
        presentationDTO.userName = presentation.getUserName();
        presentationDTO.date = presentation.getDate().toString();
        presentationDTO.detail = presentation.getDetail();
        presentationDTO.title = presentation.getTitle();
        presentationDTO.subject = presentation.getSubject();
        presentationDTO.time = PresentationTime.getOrdinalByDescription(presentation.getTime());
        presentationDTO.status = PresentationStatus.getOrdinalByDescription(presentation.getStatus());
        presentationDTO.type = PresentationType.valueOf(presentation.getType()).ordinal();
        presentationDTO.screen = presentation.getScreen();
        presentationDTO.intraId = presentation.getIntraId();

        return presentationDTO;
    }

    public void setDate(String wednesday) {
        this.date = wednesday;
    }
}
