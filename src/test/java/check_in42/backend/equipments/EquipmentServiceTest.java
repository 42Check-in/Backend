package check_in42.backend.equipments;

import check_in42.backend.user.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EquipmentServiceTest {

    @Autowired EquipmentService equipmentService;
    @Autowired UserService userService;

    @Test
//    @Rollback(value=false) db에 남기고싶을 때
    public void create_form() throws Exception {
        //given
        EquipmentDTO test =  EquipmentDTO.dummy("박소현", "24223333", "2020-03-03", 0, false, "디테일",
                "베네핏", 3, "2023-04-04", 2L);
        Equipment equipment = equipmentService.create("sohyupar", test);

        //when
        Long equipmentFormId = equipmentService.join(equipment);
//        equipmentService.addEquipmentToUser("sohyupar", equipment);

        //then
        assertEquals(equipment, equipmentService.findOne(equipmentFormId));

    }
}