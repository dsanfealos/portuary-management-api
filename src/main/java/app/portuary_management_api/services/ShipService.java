package app.portuary_management_api.services;

import app.portuary_management_api.api.models.ModifyCrewBody;
import app.portuary_management_api.api.models.ShipDTO;
import app.portuary_management_api.entities.Dock;
import app.portuary_management_api.entities.Ship;
import app.portuary_management_api.entities.daos.ShipDAO;
import app.portuary_management_api.entities.util.ShipType;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public boolean changeDock(Ship ship, Long newDockId){
        dockService.shipExits(ship);
        Dock newDock = dockService.retrieveDock(newDockId);
        ship.setDock(newDock);
        dockService.shipEnters(ship);
        shipDAO.save(ship);
        return ship.getDock() == newDock;
    }

    public Ship modifyCrewQuantity(ModifyCrewBody body, Long id){
        Ship ship = retrieveShip(id);
        if(body.isIncrease()){
            ship.setCrewshipMembers(ship.getCrewshipMembers() + body.getQuantity());
        }else{
            ship.setCrewshipMembers(ship.getCrewshipMembers() - body.getQuantity());
        }
        return ship;
    }

    //CRUD

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

    public List<Ship> retrieveAllShips(){
        return shipDAO.findAll();
    }

    public boolean removeShip(Long id){
        try {
            Ship ship = retrieveShip(id);
            shipDAO.deleteById(id);
            return dockService.shipExits(ship);
        }catch (Exception e){
            return false;
        }
    }

    public Ship updateShip(Long id, ShipDTO dto){
        Ship ship = retrieveShip(id);
        ShipType type = ShipType.valueOf(dto.getType());
        ship.setName(dto.getName());
        ship.setCaptain(dto.getCaptain());
        ship.setCrewshipMembers(dto.getCrewshipMembers());
        ship.setType(type);
        if (!Objects.equals(dto.getDockId(), ship.getDock().getId())){
            //TODO CHECK!!
            changeDock(ship, dto.getDockId());
        }
        return shipDAO.save(ship);
    }

}
