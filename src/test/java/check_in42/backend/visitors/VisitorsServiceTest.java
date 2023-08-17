package check_in42.backend.visitors;

import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import check_in42.backend.visitors.utils.PriorApproval;
import check_in42.backend.visitors.utils.VisitorsDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Rollback
@SpringBootTest
class VisitorsServiceTest {

    @Autowired EntityManager em;
    @Autowired VisitorsService visitorsService;
    @Autowired UserService userService;
    VisitorsDTO visitorsDTO;
    PriorApproval priorApproval;
    @BeforeEach
    @DisplayName("requestBody로 받을 json 내용")
    void setVisitorsDTO() {
        visitorsDTO = VisitorsDTO.builder()
                .visitorsId(null)
                .intraId("suhwpark")
                .visitorsName("상준")
                .visitDate(LocalDate.now())
                .visitTime("13:00")
                .visitPurpose(1)
                .visitPlace(0)
                .relationWithUser(2)
                .agreement(true)
                .build();
    }
    @BeforeEach
    @DisplayName("사전 승인 폼 작성에 사용될 form 형식")
    void setPriorApproval() {
        priorApproval = new PriorApproval(visitorsDTO);
    }
    @Test
    @DisplayName("외부인 신청이 DB에 잘 저장되었는지 확인하는 것")
    void applyVisitorForm() {
        //given
        User user = createUser();
        Visitors visitors = createVisitors(user);
        //when
        Long id = visitorsService.applyVisitorForm(user, visitors);
        //then
        assertEquals(visitors, visitorsService.findById(id).get());
    }

    @Test
    @DisplayName("신청한 모든 작성 폼이 잘 db에서 불러오는지 확인")
    void findAll() {
        //given
        User user = createUser();

        userService.join(user);
        Visitors visitors1 = createVisitors(user);
        Visitors visitors2 = createVisitors(user);
        Visitors visitors3 = createVisitors(user);
        Visitors visitors4 = createVisitors(user);
        visitorsService.applyVisitorForm(user, visitors1);
        visitorsService.applyVisitorForm(user, visitors2);
        visitorsService.applyVisitorForm(user, visitors3);
        visitorsService.applyVisitorForm(user, visitors4);
        //when
        List<Visitors> result = visitorsService.findAll();

        //then
        assertEquals(4, result.size());
    }

    @Test
    void vocalConfirm() {
        //given
        User user = createUser();

        userService.join(user);
        Visitors visitors1 = createVisitors(user);
        Visitors visitors2 = createVisitors(user);
        Visitors visitors3 = createVisitors(user);
        Visitors visitors4 = createVisitors(user);
        Long id1 = visitorsService.applyVisitorForm(user, visitors1);
        Long id2 = visitorsService.applyVisitorForm(user, visitors2);
        Long id3 = visitorsService.applyVisitorForm(user, visitors3);
        Long id4 = visitorsService.applyVisitorForm(user, visitors4);
        List<Long> formIds = new ArrayList<>();
        formIds.add(id1);
        formIds.add(id2);
        formIds.add(id3);
        formIds.add(id4);

        //when
        visitorsService.vocalConfirm(formIds);

        //then
        List<Visitors> list = visitorsService.findAll();
//        list.forEach(applied -> assertEquals(applied.isConfirm(), true));
    }

    @DisplayName("임의의 유저 만들기")
    public User createUser() {
        List<Visitors> visitorsList = new ArrayList<>();
        User user = User.builder()
                .intraId("suhwpark")
                .staff(false)
                .build();
        return user;
    }

    @DisplayName("외부인 신청 만들기")
    public Visitors createVisitors(User user) {
        Visitors visitors = Visitors.builder()
                .user(user)
                .priorApproval(priorApproval)
                .build();
        return visitors;
    }

    @Test
    @DisplayName("승인된 외부인 신청만 DB에서 가져오는 기능")
    void findByApproval() {
        //given
        User user = User.builder()
                .intraId("suhwpark")
                .staff(false)
                .build();
        userService.join(user);

        Visitors visitors1 = createVisitors(user);
        System.out.println(visitors1.getApproval());
        Long id1 = visitorsService.applyVisitorForm(user, visitors1);

        Visitors visitors2 = createVisitors(user);
        System.out.println(visitors2.getApproval());
        Long id2 = visitorsService.applyVisitorForm(user, visitors2);

        Visitors visitors3 = createVisitors(user);
        Long id3 = visitorsService.applyVisitorForm(user, visitors3);
        System.out.println(visitors3.getApproval());

        Visitors visitors4 = createVisitors(user);
        System.out.println(visitors4.getApproval());
        Long id4 = visitorsService.applyVisitorForm(user, visitors4);

        //when
        List<Long> formId = new ArrayList<>();
        formId.add(id1);
        formId.add(id2);
        visitorsService.vocalConfirm(formId);
        System.out.println(visitors1.getApproval());
        System.out.println(visitors2.getApproval());
        //then
        List<Visitors> approvalList = visitorsService.findByApproval();
        assertEquals(approvalList.size(), 2);
    }
}