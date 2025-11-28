package api.portuary_management_api.entities.daos;

import api.portuary_management_api.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenDAO extends JpaRepository<VerificationToken, Long> {
}
