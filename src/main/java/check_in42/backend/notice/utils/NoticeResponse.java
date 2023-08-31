package check_in42.backend.notice.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoticeResponse {

    private List<NoticeDTO> noticeDTOList;
    private int noticeCount;

    public static NoticeResponse create(List<NoticeDTO> noticeDTOList, int noticeCount) {
        NoticeResponse noticeResponse = new NoticeResponse();
        noticeResponse.noticeDTOList = noticeDTOList;
        noticeResponse.noticeCount = noticeCount;

        return noticeResponse;
    }
}
