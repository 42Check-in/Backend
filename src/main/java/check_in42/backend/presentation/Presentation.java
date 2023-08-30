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
import lombok.RequiredArgsConstructor;

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

    private String title; // 발표 제목

    private String subject; // 발표 내용

    @Column(length = 1500)
    private String detail; // 상세 내용

    private String time; // (enum) 15, 30, 45, 1시간

    private String type; // 유형 겁나 많음 enum

    private int screen; // 촬영 희망/비희망

    private String status;

    private LocalDate approval;

    private boolean notice;


    @Builder
    protected Presentation(User user, PresentationDTO presentationDTO, int count) {

        this.intraId = user.getIntraId();
        this.userName = presentationDTO.getUserName();
        this.title = presentationDTO.getTitle();
        this.screen = presentationDTO.getScreen();
        this.detail = presentationDTO.getDetail();
        this.subject = presentationDTO.getSubject();
        this.date = LocalDate.parse(presentationDTO.getDate());
        this.time = PresentationTime.values()[presentationDTO.getTime()].getName();
        this.type = PresentationType.values()[presentationDTO.getType()].toString();
        if (count == 0) {
            this.status = PresentationStatus.PENDING.getName();
        } else
            this.status = PresentationStatus.WAITING.getName();
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
        this.status = PresentationStatus.values()[status].getName();
    }

}
