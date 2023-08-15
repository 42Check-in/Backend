package check_in42.backend.equipments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @PostMapping("/equipments/form/new")
    public ResponseEntity createNewForm(@CookieValue String intraId, @RequestBody EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentService.create(intraId, equipmentDTO);
        Long equipmentFormId = equipmentService.join(equipment);
        /*
        * user에 이 form을 저장하는 부분이 필요함
        * */
        return ResponseEntity.ok().body(equipmentFormId);
    }


    @GetMapping("/equipments/form/extension")
    public ResponseEntity<EquipmentDTO> showExtensionForm(@CookieValue String intraId) {

    }

    /*
     * intraId -> user 객체 찾아
     * user 객체에서 작성한 equipForm 찾아
     * returnDate를 period만큼 더해서 set 해줘
     * */
    @PostMapping("/equipments/form/extension")
    public ResponseEntity postExtensionForm(@CookieValue String intraId, @RequestBody EquipmentDTO equipmentDTO) {

    }

    @PostMapping("/equipments/cancel")
    public ResponseEntity cancel(@RequestParam Long formId) {
        equipmentService.findAndDelete(formId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
