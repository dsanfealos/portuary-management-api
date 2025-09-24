package api.portuary_management_api.services;

import api.portuary_management_api.api.models.DockDTO;
import api.portuary_management_api.entities.Dock;
import api.portuary_management_api.entities.Ship;
import api.portuary_management_api.entities.daos.DockDAO;
import api.portuary_management_api.entities.util.ShipSize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DockService {

    private final DockDAO dockDAO;

    public DockService(DockDAO dockDAO) {
        this.dockDAO = dockDAO;
    }

    public void shipEnters(Ship ship){
        Dock dock = ship.getDock();
        int berths = checkBerths(ship);
        if (!dock.isFull()) dock.increaseOccupied(berths);
    }

    public void shipExits(Ship ship){
        Dock dock = ship.getDock();
        int berths = checkBerths(ship);
        dock.decreaseOccupied(berths);
    }

    public int checkBerths(Ship ship){
        ShipSize size = ship.howBig();
        int berths = 0;
        switch (size){
            case SMALL -> berths = 1;
            case REGULAR -> berths = 3;
            case BIG -> berths = 7;
            case HUGE -> berths = 15;
        }
        return berths;
    }

    public List<Ship> listTypeInDock(Long id, String type){
        Dock dock = retrieveDock(id);
        return dock.getShips().stream()
                .filter(ship -> Objects.equals(ship.getType().name(), type))
                .toList();
    }

    public List<Ship> listSizeInDock(Long id, String size){
        Dock dock = retrieveDock(id);
        return dock.getShips().stream()
                .filter(ship -> Objects.equals(ship.howBig().name(), size))
                .toList();
    }

    //CRUD

    public Dock createDock(DockDTO dto){
        Dock dock = new Dock();
        dock.setName(dto.getName());
        dock.setCity(dto.getCity());
        dock.setCapacity(dto.getCapacity());
        dock.setOccupied(dto.getOccupied());
        return dockDAO.save(dock);
    }

    public Dock retrieveDock(Long id){
        Optional<Dock> optDock = dockDAO.findById(id);
        return optDock.orElse(null);
    }

    public Dock updateDock(Long id, DockDTO dto){
        Dock dock = retrieveDock(id);
        dock.setOccupied(dto.getOccupied());
        dock.setName(dto.getName());
        dock.setCapacity(dto.getCapacity());
        dock.setCity(dto.getCity());

        return dock;
    }

}
