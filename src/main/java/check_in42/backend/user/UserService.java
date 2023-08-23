package check_in42.backend.user;

import check_in42.backend.presentation.Presentation;
import check_in42.backend.presentation.PresentationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
