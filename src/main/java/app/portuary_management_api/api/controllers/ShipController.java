package app.portuary_management_api.api.controllers;

import app.portuary_management_api.api.models.ModifyCrewBody;
import app.portuary_management_api.api.models.ChangeDockBody;
import app.portuary_management_api.api.models.dtos.ShipDTO;
import app.portuary_management_api.entities.Ship;
import app.portuary_management_api.services.ShipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ship")
public class ShipController {

    private final ShipService shipService;

    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }


    @PostMapping("/change-dock")
    public ResponseEntity<Ship> changeDock(@RequestBody ChangeDockBody body){
        Ship ship = shipService.retrieve(body.getShipId());
        if (shipService.changeDock(ship, body.getNewDockId())){
            return new ResponseEntity<>(ship, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    //EP. Cargar/Descargar Mercancía


    @PostMapping("/{id}/crew")
    public ResponseEntity<Ship> changeCrewQuantity(@RequestBody ModifyCrewBody body, @PathVariable Long id){
        Ship ship = shipService.modifyCrewQuantity(body, id);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    //CRUD

    @PostMapping
    public ResponseEntity<Ship> createShip(@RequestBody ShipDTO body){
        Ship ship = shipService.create(body);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ship> getShip(@PathVariable Long id){
        Ship ship = shipService.retrieve(id);
        return (ship!= null) ? new ResponseEntity<>(ship, HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<Ship>> getAllShips(){
        List<Ship> list = shipService.retrieveAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShip(@PathVariable Long id){
        if (shipService.remove(id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable Long id, @RequestBody ShipDTO body){
        Ship ship = shipService.update(id, body);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }
}
