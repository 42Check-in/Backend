package check_in42.backend.user;

import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoom;
import check_in42.backend.conferenceRoom.ConferenceRoom.ConferenceRoomDTO;
import check_in42.backend.equipments.Equipment;
import check_in42.backend.presentation.Presentation;
import check_in42.backend.presentation.PresentationRepository;
import check_in42.backend.user.exception.UserRunTimeException;
import check_in42.backend.visitors.Visitors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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

    public List<ConferenceRoom> findConferenceList (String intraId) {
        final User user = this.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<ConferenceRoom> conferenceRoomList = user.getConferenceRooms();
        return conferenceRoomList;
    }

    public List<Visitors> findVisitorList (String intraId) {
        final User user = this.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Visitors> visitorsList = user.getVisitors();
        return visitorsList;
    }

    public List<Presentation> findPresentationList (String intraId) {
        final User user = this.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Presentation> presentationList = user.getPresentations();
        return presentationList;
    }

    public List<Equipment> findEquipmentList (String intraId) {
        final User user = this.findByName(intraId)
                .orElseThrow(UserRunTimeException.NoUserException::new);
        final List<Equipment> equipmentList = user.getEquipments();
        return equipmentList;
    }
}
