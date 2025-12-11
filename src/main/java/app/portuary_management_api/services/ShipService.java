package app.portuary_management_api.services;

import app.portuary_management_api.api.models.ModifyCrewBody;
import app.portuary_management_api.api.models.dtos.ShipDTO;
import app.portuary_management_api.entities.Dock;
import app.portuary_management_api.entities.Freight;
import app.portuary_management_api.entities.Ship;
import app.portuary_management_api.entities.daos.ShipDAO;
import app.portuary_management_api.entities.util.ShipType;
import app.portuary_management_api.services.util.FreightUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ShipService implements CRUDServiceInterface<Ship, ShipDTO>{

    private final ShipDAO shipDAO;
    private final DockService dockService;

    public ShipService(ShipDAO shipDAO, DockService dockService) {
        this.shipDAO = shipDAO;
        this.dockService = dockService;
    }

    public boolean changeDock(Ship ship, Long newDockId){
        dockService.shipExits(ship);
        Dock newDock = dockService.retrieve(newDockId);
        ship.setDock(newDock);
        dockService.shipEnters(ship);
        shipDAO.save(ship);
        return ship.getDock() == newDock;
    }

    public Ship modifyCrewQuantity(ModifyCrewBody body, Long id){
        Ship ship = retrieve(id);
        if(body.isIncrease()){
            ship.setCrewshipMembers(ship.getCrewshipMembers() + body.getQuantity());
        }else{
            ship.setCrewshipMembers(ship.getCrewshipMembers() - body.getQuantity());
        }
        return ship;
    }

    public Integer howManyItemsOfFreight(String freightType, Long shipId){
        Ship ship = retrieve(shipId);
        if (ship != null){
            List<Freight> freights = ship.getFreights();
            return FreightUtils.howManyItemsOfFreight(freightType, freights);
        }
        return null;
    }

    //CRUD

    @Override
    public Ship create(ShipDTO dto){
        Ship ship = new Ship();
        mapDtoToEntity(dto, ship);
        return shipDAO.save(ship);
    }

    @Override
    public Ship retrieve(Long id){
        Optional<Ship> optShip = shipDAO.findById(id);
        return optShip.orElse(null);
    }

    @Override
    public List<Ship> retrieveAll(){
        return shipDAO.findAll();
    }

    @Override
    public boolean remove(Long id){
        try {
            Ship ship = retrieve(id);
            shipDAO.deleteById(id);
            return dockService.shipExits(ship);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Ship update(Long id, ShipDTO dto){
        Ship ship = retrieve(id);
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

    private void mapDtoToEntity(ShipDTO dto, Ship ship){
        ShipType type = ShipType.valueOf(dto.getType());
        Dock dock = dockService.retrieve(dto.getDockId());
        ship.setCaptain(dto.getCaptain());
        ship.setCrewshipMembers(dto.getCrewshipMembers());
        ship.setDock(dock);
        ship.setType(type);
        ship.setName(dto.getName());
    }
}
