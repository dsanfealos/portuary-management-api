package app.portuary_management_api.entities.daos;

import app.portuary_management_api.entities.Dock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DockDAO extends JpaRepository<Dock, Long> {
}
