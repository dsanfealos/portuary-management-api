package api.portuary_management_api.services;

import api.portuary_management_api.api.dto.ShipDTO;
import api.portuary_management_api.entities.Dock;
import api.portuary_management_api.entities.Ship;
import api.portuary_management_api.entities.daos.ShipDAO;
import api.portuary_management_api.entities.util.ShipType;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ShipService {

    private final ShipDAO shipDAO;
    private final DockService dockService;

    public ShipService(ShipDAO shipDAO, DockService dockService) {
        this.shipDAO = shipDAO;
        this.dockService = dockService;
    }

    public void changeDock(Ship ship, Dock newDock){
        dockService.shipExits(ship);
        ship.setDock(newDock);
        dockService.shipEnters(ship);
    }

    public Ship createShip(ShipDTO dto){
        ShipType type = ShipType.valueOf(dto.getType());
        Dock dock = dockService.retrieveDock(dto.getDockId());
        Ship ship = new Ship(dto.getName(), dto.getCaptain(),
                dto.getCrewshipMembers(), type, dock);
        dockService.shipEnters(ship);
        return shipDAO.save(ship);
    }
    public Ship retrieveShip(Long id){
        Optional<Ship> optShip = shipDAO.findById(id);
        return optShip.orElse(null);
    }
    public boolean removeShip(Long id){
        try {
            Ship ship = retrieveShip(id);
            shipDAO.deleteById(id);
            dockService.shipExits(ship);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public Ship updateShip(Long id, ShipDTO dto){
        Ship ship = retrieveShip(id);
        ShipType type = ShipType.valueOf(dto.getType());
        Dock dock = dockService.retrieveDock(dto.getDockId());
        ship.setName(dto.getName());
        ship.setCaptain(dto.getCaptain());
        ship.setCrewshipMembers(dto.getCrewshipMembers());
        ship.setType(type);
        if (!Objects.equals(dto.getDockId(), ship.getDock().getId())){
            //TODO CHECK!!
            changeDock(ship, dock);
        }
        return shipDAO.save(ship);
    }

}
