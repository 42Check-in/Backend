package check_in42.backend.notice.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class NoticeDTO {

    private int category;

    private Long formId;

    private LocalDate agreeDate;

    private boolean notice;

    @Builder
    protected NoticeDTO(int category, Long formId, LocalDate agreeDate, boolean notice) {
        this.category = category;
        this.formId = formId;
        this.agreeDate = agreeDate;
        this.notice = notice;
    }
}
