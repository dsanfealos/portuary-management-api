package app.portuary_management_api.services;

import app.portuary_management_api.api.models.dtos.DockDTO;
import app.portuary_management_api.entities.Dock;
import app.portuary_management_api.entities.Freight;
import app.portuary_management_api.entities.Ship;
import app.portuary_management_api.entities.daos.DockDAO;
import app.portuary_management_api.entities.util.ShipSize;
import app.portuary_management_api.services.util.FreightUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DockService implements CRUDServiceInterface<Dock, DockDTO>{

    private final DockDAO dockDAO;

    public DockService(DockDAO dockDAO) {
        this.dockDAO = dockDAO;
    }

    public void shipEnters(Ship ship, Dock dock){
        int berths = checkBerths(ship);
        if (!dock.isFull()) dock.increaseOccupied(berths);
    }

    public boolean shipExits(Ship ship){
        Dock dock = ship.getDock();
        int berths = checkBerths(ship);
        dock.decreaseOccupied(berths);
        return ship.leaveDock();
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
        Dock dock = retrieve(id);
        return dock.getShips().stream()
                .filter(ship -> Objects.equals(ship.getType().name(), type))
                .toList();
    }

    public List<Ship> listSizeInDock(Long id, String size){
        Dock dock = retrieve(id);
        return dock.getShips().stream()
                .filter(ship -> Objects.equals(ship.howBig().name(), size))
                .toList();
    }

    public Integer howManyItemsOfFreight(String freightType, Long dockId){
        Dock dock = retrieve(dockId);
        if (dock != null){
            List<Freight> freights = dock.getFreights();
            return FreightUtils.howManyItemsOfFreight(freightType, freights);
        }
        return null;
    }

    public Integer howManyDifferentFreightsOfType(String freightType, Long dockId){
        Dock dock = retrieve(dockId);
        if (dock != null){
            List<Freight> freights = dock.getFreights();
            return FreightUtils.howManyDifferentFreightsOfType(freightType, freights);
        }
        return null;
    }

    //CRUD

    @Override
    public Dock create(DockDTO dto){
        Dock dock = new Dock();
        mapDtoToEntity(dto, dock);
        return dockDAO.save(dock);
    }

    @Override
    public Dock retrieve(Long id){
        Optional<Dock> optDock = dockDAO.findById(id);
        return optDock.orElse(null);
    }

    @Override
    public Dock update(Long id, DockDTO dto){
        Dock dock = retrieve(id);
        mapDtoToEntity(dto, dock);
        return dock;
    }

    @Override
    public boolean remove(Long id){
        try{
            dockDAO.deleteById(id);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<Dock> retrieveAll(){
        return dockDAO.findAll();
    }


    private void mapDtoToEntity(DockDTO dto, Dock entity){
        entity.setOccupied(dto.getOccupied());
        entity.setName(dto.getName());
        entity.setCapacity(dto.getCapacity());
        entity.setCity(dto.getCity());
    }

}
