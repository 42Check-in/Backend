package check_in42.backend.vocal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Vocal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String intraId;
}
