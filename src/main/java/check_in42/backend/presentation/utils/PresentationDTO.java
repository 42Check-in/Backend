package check_in42.backend.presentation.utils;

import check_in42.backend.presentation.Presentation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
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
/*
*   1. Long formId (이미 신청된 폼 있다면 대기열 올리게)
    2. String userName
    3. String date
    4. String subject // 발표 제목
    5. String contents // 발표 내용
    6. String detail // 상세 내용
    7. Long time // (enum) 15, 30, 45, 1시간
    8. Long type // 유형 겁나 많음 enum
    9. Boolean screen // 촬영 희망/비희망
*
* */
