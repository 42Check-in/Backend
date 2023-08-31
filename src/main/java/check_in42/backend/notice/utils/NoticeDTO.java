package check_in42.backend.notice.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeDTO {

    private Long category;

    private Long formId;

    private LocalDate date;

    private boolean notice;

}
