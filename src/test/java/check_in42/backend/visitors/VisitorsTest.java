package check_in42.backend.visitors;

import check_in42.backend.user.User;
import check_in42.backend.visitors.visitUtils.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class VisitorsTest {

    PriorApproval priorApproval;
    VisitorsDTO visitorsDTO;

    @BeforeEach
    @DisplayName("requestBody로 받을 json 내용")
    void setVisitorsDTO() {
        visitorsDTO = VisitorsDTO.builder()
                .visitorsId(null)
                .intraId("suhwpark")
                .visitorsName("상준")
                .visitDate(new Date())
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
    @DisplayName("보컬이 승인을 눌렀을 때 true로 바뀌는지에 대한 검사")
    void vocalConfirm() {
        //given
        User user = User.builder()
                .intraId("suhwpark")
                .staff(false)
                .build();
        Visitors visitors = Visitors.builder()
                .user(user)
                .priorApproval(priorApproval)
                .build();
        //when
        visitors.vocalConfirm();
        //then
        assertEquals(visitors.isConfirm(), true);
    }

    @Test
    @DisplayName("resquest로 받아온 정보들이 잘 들어가는지에 대한 검사")
    void builder() {
        //given
        User user = User.builder()
                .intraId("suhwpark")
                .staff(false)
                .build();
        //when
        Visitors visitors = Visitors.builder()
                .user(user)
                .priorApproval(priorApproval)
                .build();
        //then
        assertEquals(visitors.isConfirm(), false);
        assertEquals(visitors.getUser(), user);
        assertEquals(visitors.getPriorApproval().getVisitPurpose(), VisitPurpose.STUDYING);
        assertEquals(visitors.getPriorApproval().getRelationWithUser(), RelationWithUser.FAMILY);
        assertEquals(visitors.getPriorApproval().getVisitPlace(), VisitPlace.B1);
    }
}