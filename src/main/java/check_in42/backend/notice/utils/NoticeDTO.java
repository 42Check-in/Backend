package check_in42.backend.notice.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeDTO {

    private Long category;

    private Long formId;

    private LocalDate date;

    private boolean notice;

    public static NoticeDTO create(Long category, Long formId, Timestamp date, boolean notice) {
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.category = category;
        noticeDTO.formId = formId;
        noticeDTO.date = date.toLocalDateTime().toLocalDate();
        noticeDTO.notice = notice;
        return noticeDTO;
    }

}
