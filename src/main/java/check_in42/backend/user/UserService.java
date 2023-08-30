package check_in42.backend.user;

import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.equipments.Equipment;
import check_in42.backend.equipments.EquipmentDTO;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.presentation.utils.PresentationDTO;
import check_in42.backend.user.exception.UserRunTimeException;
import check_in42.backend.visitors.Visitors;
import check_in42.backend.visitors.visitUtils.VisitorsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Transactional
    public User create(String intraId, boolean staff, String refreshToken) {
        User user = User.builder()
                .intraId(intraId)
                .staff(staff)
                .refreshToken(refreshToken).build();
        userRepository.save(user);
        return user;
    }
    @Transactional
    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    public Optional<User> findByName(String intraId) {
        return userRepository.findByName(intraId);
    }

    public Optional<User> findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }

    public List<ConferenceRoomDTO> findConferenceList (String intraId) {
        final User user = this.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<ConferenceRoom> conferenceRoomList = user.getConferenceRooms();
        Comparator<ConferenceRoom> descendingComparator = (v1, v2) -> v2.getId().compareTo(v1.getId());
        final List<ConferenceRoomDTO> result = conferenceRoomList.stream()
                .sorted(descendingComparator)
                .map(ConferenceRoomDTO::create)
                .collect(Collectors.toList());
        return result;
    }

    public List<VisitorsDTO> findVisitorList (String intraId) {
        final User user = this.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Visitors> visitorsList = user.getVisitors();
        Comparator<Visitors> descendingComparator = (v1, v2) -> v2.getId().compareTo(v1.getId());
        final List<VisitorsDTO> result = visitorsList.stream()
                .sorted(descendingComparator)
                .map(VisitorsDTO::create)
                .collect(Collectors.toList());
        return result;
    }

    public List<PresentationDTO> findPresentationList (String intraId) {
        final User user = this.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Presentation> presentationList = user.getPresentations();
        Comparator<Presentation> descendingComparator = (v1, v2) -> v2.getId().compareTo(v1.getId());
        final List<PresentationDTO> result = presentationList.stream()
                .sorted(descendingComparator)
                .map(PresentationDTO::create)
                .collect(Collectors.toList());
        return result;
    }

    public List<EquipmentDTO> findEquipmentList (String intraId) {
        final User user = this.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Equipment> equipmentList = user.getEquipments();
        Comparator<Equipment> descendingComparator = (v1, v2) -> v2.getId().compareTo(v1.getId());
        final List<EquipmentDTO> result = equipmentList.stream()
                .sorted(descendingComparator)
                .map(EquipmentDTO::create)
                .collect(Collectors.toList());
        return result;
    }
}
