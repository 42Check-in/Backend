package check_in42.backend.notice;

import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Notice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Long formId;

    private Integer category;

    private LocalDateTime approval;

    private Boolean notice;

    @Builder
    protected Notice(User user, Long formId, Integer category) {
        this.user = user;
        this.formId = formId;
        this.category = category;
        this.approval = LocalDateTime.now();
        this.notice = false;
    }

    public void setNotice(Boolean notice) {
        this.notice = notice;
    }
}
