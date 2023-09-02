package check_in42.backend.notice;

import check_in42.backend.equipments.Equipment;
import check_in42.backend.equipments.EquipmentService;
import check_in42.backend.notice.utils.CategoryType;
import check_in42.backend.notice.utils.NoticeDTO;
import check_in42.backend.notice.utils.NoticeResponse;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.presentation.PresentationService;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.user.exception.UserRunTimeException;
import check_in42.backend.visitors.Visitors;
import check_in42.backend.visitors.VisitorsService;
import check_in42.backend.vocal.utils.FormIdList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final VisitorsService visitorsService;
    private final EquipmentService equipmentService;
    private final PresentationService presentationService;
    private final UserService userService;

    public Notice create(User user, NoticeDTO noticeDTO) {
        return Notice.builder()
                .user(user)
                .formId(noticeDTO.getFormId())
                .category(noticeDTO.getCategory())
                .build();
    }

    public void inputNoticeVisitors(FormIdList formIdList) {
        formIdList.getFormIds().forEach(id -> {
            Visitors visitors = visitorsService.findById(id)
                    .orElseThrow(UserRunTimeException.NoUserException::new);
            Notice notice = Notice.builder()
                    .user(visitors.getUser())
                    .formId(id)
                    .category(CategoryType.VISITORS.ordinal())
                    .build();
            noticeRepository.save(notice);
            visitors.getUser().addNotice(notice);
        });
    }

    public void inputNoticePresentations(FormIdList formIdList) {
        formIdList.getFormIds().forEach(id -> {
            Presentation presentation = presentationService.findOne(id);
            Notice notice = Notice.builder()
                    .user(presentation.getUser())
                    .formId(id)
                    .category(CategoryType.PRESENTATION.ordinal())
                    .build();
            noticeRepository.save(notice);
            presentation.getUser().addNotice(notice);
        });
    }

    public void inputNoticeEquipments(FormIdList formIdList) {
        formIdList.getFormIds().forEach(id -> {
            Equipment equipment = equipmentService.findOne(id);
            Notice notice = Notice.builder()
                    .user(equipment.getUser())
                    .formId(id)
                    .category(CategoryType.EQUIPMENT.ordinal())
                    .build();
            noticeRepository.save(notice);
            equipment.getUser().addNotice(notice);
        });
    }

    public void deleteThreeDaysAgo() {
        long threeDayTimeMillis = 3 * 24 * 60 * 60 * 1000;
        noticeRepository.deleteByThreeDaysAgo(new Date(System.currentTimeMillis() - threeDayTimeMillis));
    }

    public List<NoticeDTO> showNotice(String intraId) {
        List<NoticeDTO> noticeDTOS = new ArrayList<>();
        List<Notice> notices = noticeRepository.findAllByUser(userService.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new));
        notices.forEach(notice -> noticeDTOS.add(new NoticeDTO(notice)));
        return noticeDTOS;
    }

    @Transactional
    public Long updateNotice(String intraId) {
        final User user = userService.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        List<Notice> notices = noticeRepository.findAllByUser(user);
        notices.forEach(notice -> notice.setNotice(true));
        return user.getId();
    }
}
