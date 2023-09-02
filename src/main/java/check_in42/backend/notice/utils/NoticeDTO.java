package check_in42.backend.notice.utils;

import check_in42.backend.notice.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class NoticeDTO {

    private Long formId;

    private Integer category;

    private LocalDateTime approval;

    private Boolean notice;

    public NoticeDTO(Notice notice) {
        this.formId = notice.getFormId();
        this.category = notice.getCategory();
        this.approval = notice.getApproval();
        this.notice = notice.getNotice();
    }
}
