package api.portuary_management_api.api.controllers;

import api.portuary_management_api.api.models.DockDTO;
import api.portuary_management_api.entities.Dock;
import api.portuary_management_api.entities.Ship;
import api.portuary_management_api.services.DockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dock")
public class DockController {

    private final DockService dockService;

    public DockController(DockService dockService) {
        this.dockService = dockService;
    }

    //EP. Ver Barcos de de Dock X con tipo Y
    @GetMapping("/{id}/{type}")
    public ResponseEntity<List<Ship>> listTypeInDock(@PathVariable Long id, @PathVariable String type){
        List<Ship> ships = dockService.listTypeInDock(id, type);
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    //EP. Ver Barcos de Dock X con tamaño Z
    @GetMapping("/{id}/{size}")
    public ResponseEntity<List<Ship>> listSizeInDock(@PathVariable Long id, @PathVariable String size){
        List<Ship> ships = dockService.listSizeInDock(id, size);
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }


    //CRUD

    @PostMapping
    public ResponseEntity<Dock> createDock(@RequestBody DockDTO body){
        Dock dock = dockService.createDock(body);
        return new ResponseEntity<>(dock, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dock> getDock(@PathVariable Long id){
        Dock dock = dockService.retrieveDock(id);
        return dock != null? new ResponseEntity<>(dock, HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dock> updateDock(@PathVariable Long id, @RequestBody DockDTO body){
        Dock dock = dockService.updateDock(id, body);
        return null;
    }

}
