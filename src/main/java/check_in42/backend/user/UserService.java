package check_in42.backend.user;

import check_in42.backend.presentation.Presentation;
import check_in42.backend.presentation.PresentationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findByName(String intraId) {
        return userRepository.findByName(intraId);
    }

}
