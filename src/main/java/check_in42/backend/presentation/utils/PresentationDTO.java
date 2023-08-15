package check_in42.backend.presentation.utils;

import check_in42.backend.equipments.EquipmentDTO;
import lombok.Getter;

import java.time.LocalDate;

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
    private Boolean screen;

    public static PresentationDTO create(Long formId, String userName, String subject, LocalDate date, int type,
                                      String detail, String contents, int time, boolean screen) {
        PresentationDTO presentationDTO = new PresentationDTO();
        presentationDTO.formId = formId;
        presentationDTO.userName = userName;
        presentationDTO.date = date.toString();
        presentationDTO.detail = detail;
        presentationDTO.subject = subject;
        presentationDTO.contents = contents;
        presentationDTO.time = time;
        presentationDTO.type = type;
        presentationDTO.screen = screen;

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
