package app.portuary_management_api.api.controllers;

import app.portuary_management_api.api.models.dtos.FreightDTO;
import app.portuary_management_api.entities.Freight;
import app.portuary_management_api.services.FreightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/freight")
public class FreightController {

    private final FreightService freightService;

    public FreightController(FreightService freightService) {
        this.freightService = freightService;
    }

    @PostMapping
    public ResponseEntity<Freight> createFreight(@RequestBody FreightDTO body){
        Freight freight = freightService.create(body);
        if (freight == null){
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(freight, HttpStatus.OK);
    }

    @GetMapping("/{freightId}")
    public ResponseEntity<Freight> getFreight(@PathVariable Long freightId){
        Freight freight = freightService.retrieve(freightId);
        if (freight == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(freight, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Freight>> getAllFreights(){
        List<Freight> freightList = freightService.retrieveAll();
        if (freightList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(freightList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Freight> updateDock(@PathVariable Long id, @RequestBody FreightDTO body){
        Freight freight = freightService.update(id, body);
        if (freight == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(freight, HttpStatus.OK);
    }

    @DeleteMapping("/{freightId}")
    public ResponseEntity<Void> deleteFreight(@PathVariable Long freightId){
        if (freightService.remove(freightId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
