package check_in42.backend.user;

import check_in42.backend.conferenceRoom.ConferenceRoom;
import check_in42.backend.equipments.Equipment;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.visitor.Visitor;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String intraId;

    @OneToMany(mappedBy = "user")
    private List<ConferenceRoom> conferenceRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Visitor> visitors = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Presentation> presentations = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Equipment> equipments = new ArrayList<>();
}
