package check_in42.backend.equipments;

import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class EquipmentServiceTest {

    @Autowired EquipmentService equipmentService;
    @Autowired UserService userService;

    @Before
    public void set_up() {
        User user = userService.findByName("sohyupar").get();
        EquipmentDTO equipmentDTO =
    }

    @Test
    public void 유저_폼_작성() throws Exception {
        //given

        //when


        //then

    }

}