package check_in42.backend.presentation;

import check_in42.backend.presentation.utils.PresentationStatus;
import check_in42.backend.presentation.utils.PresentationTime;
import check_in42.backend.presentation.utils.PresentationType;
import check_in42.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Presentation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String userName;

    private String date;

    private String subject; // 발표 제목

    private String contents; // 발표 내용

    private String detail; // 상세 내용

    private PresentationTime time; // (enum) 15, 30, 45, 1시간

    private PresentationType type; // 유형 겁나 많음 enum

    private Boolean screen; // 촬영 희망/비희망

    private PresentationStatus status;
}
