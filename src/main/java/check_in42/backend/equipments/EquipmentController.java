package check_in42.backend.equipments;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class EquipmentController {

    @PostMapping("/equipments/form/new")
    public ResponseEntity newForm(@RequestBody EquipmentDTO equipmentDTO) {

        return new ResponseEntity(HttpStatus.OK);
    }
}
