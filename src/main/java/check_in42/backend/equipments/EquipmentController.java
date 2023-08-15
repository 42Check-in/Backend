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

    /*
    * DTO를 가공해서 db에 올라갈 형식으로 만든 후 저장
    * intraId를 갖고 user를 찾아서 list에도 equipform 추가
    * */
    @PostMapping("/equipments/form/new")
    public ResponseEntity createNewForm(@CookieValue String intraId, @RequestBody EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentService.create(intraId, equipmentDTO);
        Long equipmentFormId = equipmentService.join(equipment);
        equipmentService.addEquipmentToUser(intraId, equipment);
        return ResponseEntity.ok().body(equipmentFormId);
    }


    /*
     * 해당 user의 기존에 작성했던 모든 equipments form 보여주기?
     * returnDate와 localDate 비교 후 기한이 남은 form만 DTO에 담아서 반환..
     * */
    @GetMapping("/equipments/form/extension")
    public List<EquipmentDTO> showExtensionForm(@CookieValue String intraId) {
        return equipmentService.showAllFormByName(intraId);
    }

    /*
     * intraId -> user 객체 찾아
     * user 객체에서 작성한 equipForm 찾아
     * returnDate를 period만큼 더해서 set 해줘 -> db는 dirtychecking으로 올라감
     *
     * user에 따로 update 해저
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
    public ResponseEntity cancel(@CookieValue String intraId, @RequestParam Long formId) {
        equipmentService.findAndDelete(intraId, formId);
        return new ResponseEntity(HttpStatus.OK);
    }
}