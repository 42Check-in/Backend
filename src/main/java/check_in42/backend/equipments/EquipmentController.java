package check_in42.backend.equipments;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;
    private final UserService userService;

    /*
    * DTO를 가공해서 db에 올라갈 형식으로 만든 후 저장
    * intraId를 갖고 user를 찾아서 list에도 equipform 추가
    * */
    @PostMapping("/equipments/form/new")
    public ResponseEntity createNewForm(@UserId final UserInfo userInfo,
                                        @RequestBody final EquipmentDTO equipmentDTO) {
//        User user = userService.findByName(userInfo.getIntraId())
//                .orElseThrow(UserRunTimeException.NoUserException::new);;
//        Equipment equipment = equipmentService.create(user, equipmentDTO);
//        Long equipmentFormId = equipmentService.join(equipment);
//        user.addEquipForm(equipment);
        final Long equipmentFormId = equipmentService.createNewForm(userInfo.getIntraId(), equipmentDTO);
        return ResponseEntity.ok().body(equipmentFormId);
    }


    /*
     * 해당 user의 기존에 작성했던 모든 equipments form 보여주기
     * returnDate와 localDate 비교 후 기한이 남은 form만 DTO에 담아서 반환..
     * */
    @GetMapping("/equipments/form/extension")
    public ResponseEntity<List<EquipmentDTO>> showExtensionForm(@UserId final UserInfo userInfo) {
        List<EquipmentDTO> res =  equipmentService.showAllFormByName(userInfo.getIntraId());
        return ResponseEntity.ok(res);
    }

    /*
     * intraId -> user 객체 찾아
     * user 객체에서 작성한 equipForm 찾아
     * returnDate를 period만큼 더해서 set 해줘 -> db는 dirtychecking으로 올라감
     *
     * user에 따로 update 해저
     * */

    @PostMapping("/equipments/form/extension")
    public ResponseEntity postExtensionForm(@UserId final UserInfo userInfo,
                                            @RequestBody final EquipmentDTO equipmentDTO) {
        equipmentService.extendDate(userInfo.getIntraId(), equipmentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 추가적으로 user의 equipList에서도 제거하기
     * */
    @PostMapping("/equipments/cancel")
    public ResponseEntity cancel(@UserId final UserInfo userInfo, @RequestBody final EquipmentDTO equipmentDTO) {
        equipmentService.findAndDelete(userInfo.getIntraId(), equipmentDTO.getFormId());
        return new ResponseEntity(HttpStatus.OK);
    }
}
