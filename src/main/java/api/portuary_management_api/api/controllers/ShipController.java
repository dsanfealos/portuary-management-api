package api.portuary_management_api.api.controllers;

import api.portuary_management_api.api.models.ChangeDockBody;
import api.portuary_management_api.api.models.ShipDTO;
import api.portuary_management_api.entities.Ship;
import api.portuary_management_api.services.ShipService;
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

    //EP. Cambiar Dock
    @PostMapping("/change-dock")
    public ResponseEntity<Ship> changeDock(@RequestBody ChangeDockBody body){
        Ship ship = shipService.retrieveShip(body.getShipId());
        shipService.changeDock(ship, body.getNewDockId());
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    //EP. Cargar/Descargar Mercancía

    //EP. Aumentar/Disminuir tripulación



    //CRUD

    @PostMapping
    public ResponseEntity<Ship> createShip(@RequestBody ShipDTO body){
        Ship ship = shipService.createShip(body);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ship> getShip(@PathVariable Long id){
        Ship ship = shipService.retrieveShip(id);
        return (ship!= null) ? new ResponseEntity<>(ship, HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<Ship>> getAllShips(){
        List<Ship> list = shipService.retrieveAllShips();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShip(@PathVariable Long id){
        if (shipService.removeShip(id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable Long id, @RequestBody ShipDTO body){
        Ship ship = shipService.updateShip(id, body);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }
}
