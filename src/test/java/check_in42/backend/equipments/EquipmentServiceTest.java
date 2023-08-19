package check_in42.backend.equipments;

import check_in42.backend.equipments.utils.EquipmentType;
import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class EquipmentServiceTest {

    @Autowired EquipmentService equipmentService;
    @Autowired UserService userService;

//    @Test
//    @Rollback(value = false) //db에 남기고싶을 때
//    public void create_form() throws Exception {
//        //given
//        EquipmentType tmp = EquipmentType.values()[0];
//        System.out.println(">>>>>>>>>>" + tmp);
//        EquipmentDTO test = EquipmentDTO.dummy("박소현", "24223333", "2020-03-03", 0, false, "디테일",
//                "베네핏", 3, "2033-04-04", 1L);
//        EquipmentDTO test2 = EquipmentDTO.dummy("박소현", "24223333", "2020-03-03", 0, false, "디테일",
//                "베네핏", 3, "2033-04-04", 2L);
//        User user = new User("sohyupar", false);
//        userService.join(user);
//        Equipment equipment = equipmentService.create(user, test);
//        Equipment equipment2 = equipmentService.create(user, test2);
//
//
//        //when
//        Long equipmentFormId = equipmentService.join(equipment);
//        System.out.println("............." + equipmentFormId);
//        user.addEquipForm(equipment);
//        user.addEquipForm(equipment2);
//
//        //then
//        assertEquals(equipment, equipmentService.findOne(equipmentFormId));
//        assertEquals(user.getEquipments().get(0).getUserName(), "박소현");
////        List<EquipmentDTO> res = equipmentService.showAllFormByName(user.getIntraId());
////        assertEquals(2, res.size());
//    }

    @Test
    public void 폼_삭제() throws Exception {
        //given


        //when


        //then

    }
}