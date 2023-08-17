package check_in42.backend.user;

import check_in42.backend.equipments.Equipment;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.visitors.Visitors;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String intraId;

    private boolean staff;

    @Builder
    public User(String intraId, boolean staff) {
        this.intraId = intraId;
        this.staff = staff;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ConferenceRoom> conferenceRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Visitors> visitors = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Presentation> presentations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Equipment> equipments = new ArrayList<>();

    public void addEquipForm(Equipment equipment) {
        this.equipments.add(equipment);
    }

    public void addVisitorsForm(Visitors visitors) {
        this.visitors.add(visitors);
    }
    public void addConferenceForm(ConferenceRoom conferenceRoom) {
        this.conferenceRooms.add(conferenceRoom);
    }
    public void addPresentationForm(Presentation presentation) {
        this.presentations.add(presentation);
    }

    public void deleteEquipForm(Long formId) {
        this.equipments.removeIf(equipment -> equipment.getId().equals(formId));
    }

    public void deleteVisitorsForm(Long formId) {
        this.visitors.removeIf(visitors -> visitors.getId().equals(formId));
    }
//    public void deleteConferenceRoomForm(Long formId) {
//        this.conferenceRooms.removeIf(conferenceRoom -> conferenceRoom.getId().equals(formId));
//    }
    public void deletePresentationForm(Long formId) {
        this.presentations.removeIf(presentation -> presentation.getId().equals(formId));
    }
}
