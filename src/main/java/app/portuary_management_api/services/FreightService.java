package app.portuary_management_api.services;

import app.portuary_management_api.api.models.TransferFreightBody;
import app.portuary_management_api.api.models.dtos.FreightDTO;
import app.portuary_management_api.entities.Dock;
import app.portuary_management_api.entities.Freight;
import app.portuary_management_api.entities.Ship;
import app.portuary_management_api.entities.daos.FreightDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FreightService implements CRUDServiceInterface<Freight, FreightDTO>{

    private final FreightDAO freightDAO;
    private final DockService dockService;
    private final ShipService shipService;

    public FreightService(FreightDAO freightDAO, DockService dockService, ShipService shipService) {
        this.freightDAO = freightDAO;
        this.dockService = dockService;
        this.shipService = shipService;
    }



    public Freight transferFullFreightToDock(Long freightId, Long dockId){
        Freight freight = retrieve(freightId);
        freight.setDock(dockService.retrieve(dockId));
        freight.setShip(null);
        return freightDAO.save(freight);
    }

    public Freight transferFullFreightToShip(Long freightId, Long shipId){
        Freight freight = retrieve(freightId);
        freight.setDock(null);
        freight.setShip(shipService.retrieve(shipId));
        return freightDAO.save(freight);
    }

    public Freight transferPartialFreight(TransferFreightBody body){
        Freight oldFreight = retrieve(body.getFreightId());
        Freight newFreight;

        if (body.isFromShip()){
            newFreight = isThereSameTypeInDock(oldFreight.getType(), body.getDockId());
            Dock dock = dockService.retrieve(body.getDockId());
            newFreight.setDock(dock);
        }else{
            newFreight = isThereSameTypeInShip(oldFreight.getType(), body.getShipId());
            Ship ship = shipService.retrieve(body.getShipId());
            newFreight.setShip(ship);
        }

        oldFreight.substractQuantity(body.getQuantity());
        newFreight.addQuantity(body.getQuantity());
        newFreight.setType(oldFreight.getType());

        return freightDAO.save(newFreight);
    }

    private Freight isThereSameTypeInDock(String type, Long dockId){
        Dock dock = dockService.retrieve(dockId);
        List<Freight> freights = dock.getFreights().stream()
                .filter(f -> Objects.equals(f.getType(), type))
                .toList();
        return freights.isEmpty() ? new Freight() : freights.getFirst();
    }

    private Freight isThereSameTypeInShip(String type, Long shipId){
        Ship ship = shipService.retrieve(shipId);
        List<Freight> freights = ship.getFreights().stream()
                .filter(f -> Objects.equals(f.getType(), type))
                .toList();
        return freights.isEmpty() ? new Freight() : freights.getFirst();
    }

    //CRUD
    @Override
    public Freight create(FreightDTO dto) {
        Freight freight = new Freight();
        mapDtoToEntity(dto, freight);
        return freightDAO.save(freight);
    }

    @Override
    public Freight retrieve(Long id) {
        Optional<Freight> opFreight = freightDAO.findById(id);
        return opFreight.orElse(null);
    }

    @Override
    public List<Freight> retrieveAll() {
        return freightDAO.findAll();
    }

    @Override
    public boolean remove(Long id) {
        if (retrieve(id) != null){
            freightDAO.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Freight update(Long id, FreightDTO dto) {
        Freight freight = retrieve(id);
        mapDtoToEntity(dto, freight);
        return freightDAO.save(freight);
    }


    private void mapDtoToEntity(FreightDTO dto, Freight freight){
        freight.setType(dto.getType());
        freight.setQuantity(dto.getQuantity());
        if (dto.getDockId() != null){
            freight.setDock(dockService.retrieve(dto.getDockId()));
        }else{
            freight.setShip(shipService.retrieve(dto.getShipId()));
        }
    }
}
