package check_in42.backend.equipments;

import check_in42.backend.user.User;
import check_in42.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;
    private final UserService userService;

    @PostMapping("/equipments/form/new")
    public ResponseEntity createNewForm(@CookieValue String intraId, @RequestBody EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentService.create(intraId, equipmentDTO);
        Long equipmentFormId = equipmentService.join(equipment);
        return ResponseEntity.ok().body(equipmentFormId);
    }


    /*
     * 해당 user의 기존에 작성했던 모든 equipments form 보여주기?
     * */
    @GetMapping("/equipments/form/extension")
    public List<EquipmentDTO> showExtensionForm(@CookieValue String intraId) {
        return equipmentService.showAllFormByName(intraId);
    }

    /*
     * intraId -> user 객체 찾아
     * user 객체에서 작성한 equipForm 찾아
     * returnDate를 period만큼 더해서 set 해줘
     * */
    @PostMapping("/equipments/form/extension")
    public ResponseEntity postExtensionForm(@CookieValue String intraId, @RequestBody EquipmentDTO equipmentDTO) {
        equipmentService.extendDate(intraId, equipmentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 추가적으로 user의 equipList에서도 제거하기
     * */
    @PostMapping("/equipments/cancel")
    public ResponseEntity cancel(@RequestParam Long formId) {
        equipmentService.findAndDelete(formId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
