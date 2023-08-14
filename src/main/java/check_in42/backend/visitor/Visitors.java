package check_in42.backend.visitor;

import check_in42.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Visitor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
