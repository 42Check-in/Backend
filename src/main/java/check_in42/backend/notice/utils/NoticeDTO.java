package check_in42.backend.notice.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class NoticeDTO {

    private int category;

    private Long form_id;

    private LocalDate agree_date;

    private boolean notice;

    @Builder
    protected NoticeDTO(int category, Long form_id, LocalDate agree_date, boolean notice) {
        this.category = category;
        this.form_id = form_id;
        this.agree_date = agree_date;
        this.notice = notice;
    }
}
