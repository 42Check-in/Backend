package check_in42.backend.presentation;

import check_in42.backend.presentation.utils.PresentationDTO;
import check_in42.backend.presentation.utils.PresentationStatus;
import check_in42.backend.presentation.utils.PresentationTime;
import check_in42.backend.presentation.utils.PresentationType;
import check_in42.backend.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class Presentation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String userName;

    private String intraId;

    private LocalDate date;

    private String subject; // 발표 제목

    private String contents; // 발표 내용

    private String detail; // 상세 내용

    private String time; // (enum) 15, 30, 45, 1시간

    private PresentationType type; // 유형 겁나 많음 enum

    private int screen; // 촬영 희망/비희망

    private String status;

    private LocalDate approval;

    private boolean notice;


    @Builder
    protected Presentation(User user, PresentationDTO presentationDTO, int count) {

        this.intraId = user.getIntraId();
        this.userName = presentationDTO.getUserName();
        this.subject = presentationDTO.getSubject();
        this.screen = presentationDTO.getScreen();
        this.detail = presentationDTO.getDetail();
        this.contents = presentationDTO.getContents();
        this.date = LocalDate.parse(presentationDTO.getDate());
        this.time = PresentationTime.values()[presentationDTO.getTime()].getTime();
        this.type = PresentationType.values()[presentationDTO.getType()];
        if (count == 0) {
            this.type = PresentationType.valueOf(PresentationStatus.PENDING.getDescription());
        } else
            this.type = PresentationType.valueOf(PresentationStatus.WAITING.getDescription());
        this.user = user;
        this.approval = null;
        this.notice = false;
    }

    public void setApproval() {
        this.approval = LocalDate.now();
    }

    public void setNotice(boolean notice) {
        this.notice = notice;
    }

    public void setStatus(int status) {
        this.status = PresentationStatus.values()[status].getDescription();
    }

}
