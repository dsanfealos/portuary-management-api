package api.portuary_management_api.api.controllers;

import api.portuary_management_api.api.dto.DockDTO;
import api.portuary_management_api.entities.Dock;
import api.portuary_management_api.services.DockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dock")
public class DockController {

    private final DockService dockService;

    public DockController(DockService dockService) {
        this.dockService = dockService;
    }

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
    public ResponseEntity<Dock> updateDock(@PathVariable Long id){

        return null;
    }

}
