package api.portuary_management_api.services;

import api.portuary_management_api.api.dto.DockDTO;
import api.portuary_management_api.entities.Dock;
import api.portuary_management_api.entities.Ship;
import api.portuary_management_api.entities.daos.DockDAO;
import api.portuary_management_api.entities.daos.ShipDAO;
import api.portuary_management_api.entities.util.ShipSize;
import api.portuary_management_api.entities.util.ShipType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DockService {

    private DockDAO dockDAO;
    private ShipDAO shipDAO;

    public DockService(DockDAO dockDAO, ShipDAO shipDAO) {
        this.dockDAO = dockDAO;
        this.shipDAO = shipDAO;
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


}
