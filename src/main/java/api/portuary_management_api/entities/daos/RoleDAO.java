package api.portuary_management_api.entities.daos;

import api.portuary_management_api.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleDAO extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    List<Role> findByUsers_Username(String username);
}
