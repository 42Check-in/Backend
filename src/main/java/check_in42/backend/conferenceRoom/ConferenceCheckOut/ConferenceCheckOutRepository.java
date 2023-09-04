package check_in42.backend.conferenceRoom.ConferenceCheckOut;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceCheckOutRepository extends JpaRepository<ConferenceCheckOut, Long> {
}
