package check_in42.backend.presentation.utils;

import check_in42.backend.presentation.Presentation;
import lombok.Getter;

import java.util.List;

@Getter
public class PresentationDTO {

    private Long formId;
    private String userName;
    private String date;
    private String subject;
    private String contents;
    private String detail;
    private int time;
    private int type;
    private int status;
    private Boolean screen;
    private List<Long> formIds;
    private String intraId;

    public static PresentationDTO create(Presentation presentation) {
        PresentationDTO presentationDTO = new PresentationDTO();
        presentationDTO.formId = presentation.getId();
        presentationDTO.userName = presentation.getUserName();
        presentationDTO.date = presentation.getDate().toString();
        presentationDTO.detail = presentation.getDetail();
        presentationDTO.subject = presentation.getSubject();
        presentationDTO.contents = presentation.getContents();
        presentationDTO.time = PresentationTime.valueOf(presentation.getTime()).ordinal();
        presentationDTO.status = PresentationStatus.valueOf(presentation.getStatus()).ordinal();
        presentationDTO.type = PresentationType.values()[presentation.getType().ordinal()].ordinal();
        presentationDTO.screen = presentation.isScreen();
        presentationDTO.intraId = presentation.getIntraId();

        return presentationDTO;
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
