package check_in42.backend.user;

import check_in42.backend.conferenceRoom.ConferenceRoom;
import check_in42.backend.equipments.Equipment;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.visitors.Visitors;
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

    private boolean staff;

    @OneToMany(mappedBy = "user")
    private List<ConferenceRoom> conferenceRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Visitors> visitors = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Presentation> presentations = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Equipment> equipments = new ArrayList<>();

}
