package check_in42.backend.equipments;

import check_in42.backend.auth.argumentresolver.UserId;
import check_in42.backend.auth.argumentresolver.UserInfo;
import check_in42.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @PostMapping("/equipments/form/new")
    public ResponseEntity createNewForm(@UserId final UserInfo userInfo,
                                        @RequestBody final EquipmentDTO equipmentDTO) {
        log.info("-----------period?" + equipmentDTO.getPeriod());
        final Long equipmentFormId = equipmentService.createNewForm(userInfo.getIntraId(), equipmentDTO);
        return ResponseEntity.ok().body(equipmentFormId);
    }

    @GetMapping("/equipments/form/extension")
    public ResponseEntity<List<EquipmentDTO>> showExtensionForm(@UserId final UserInfo userInfo) {
        List<EquipmentDTO> res =  equipmentService.showAllFormByName(userInfo.getIntraId());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/equipments/form/extension")
    public ResponseEntity postExtensionForm(@RequestBody final EquipmentDTO equipmentDTO) {
        equipmentService.extendDate(equipmentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/equipments/cancel")
    public ResponseEntity cancel(@UserId final UserInfo userInfo, @RequestBody final EquipmentDTO equipmentDTO) {
        equipmentService.findAndDelete(userInfo.getIntraId(), equipmentDTO.getFormId());
        return new ResponseEntity(HttpStatus.OK);
    }
}
