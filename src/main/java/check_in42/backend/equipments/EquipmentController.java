package check_in42.backend.equipments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @PostMapping("/equipments/form/new")
    public ResponseEntity newForm(@RequestBody EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentService.create(equipmentDTO);
        Long equipmentFormId = equipmentService.join(equipment);
        return ResponseEntity.ok().body(equipmentFormId);
    }
}
