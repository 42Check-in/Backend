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

    private LocalDate date;

    private boolean check;

    @Builder
    protected NoticeDTO(int category, Long formId, LocalDate date, boolean check) {
        this.category = category;
        this.formId = formId;
        this.date = date;
        this.check = check;
    }

    public void checkNotice() {
        this.check = true;
    }
}
