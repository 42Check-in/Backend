package check_in42.backend.notice.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeDTO {

    private int category;

    private Long formId;

    private LocalDate date;

    private boolean notice;



    @Builder
    protected NoticeDTO(int category, Long formId, LocalDate date, boolean notice) {
        this.category = category;
        this.formId = formId;
        this.date = date;
        this.notice = notice;
    }
}
