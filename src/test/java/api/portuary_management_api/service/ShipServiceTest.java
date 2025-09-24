package api.portuary_management_api.service;

import api.portuary_management_api.entities.Dock;
import api.portuary_management_api.entities.Ship;
import api.portuary_management_api.entities.util.ShipType;
import api.portuary_management_api.services.DockService;
import api.portuary_management_api.services.ShipService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
public class ShipServiceTest {

    @Autowired
    private ShipService shipService;

    @Autowired
    private DockService dockService;

    private Ship ship;
    private Dock dockA;
    private Dock dockB;

    @BeforeEach
    public void init(){
        dockA = new Dock(2L, "Puerto del Este", "Valencia", 2500, 150
                , Collections.emptyList(), Collections.emptyList());
        dockB = new Dock(3L, "Puerto del Norte", "Bilbao", 3000, 500
                , Collections.emptyList(), Collections.emptyList());
        ship = new Ship(4L, "Merluza", "Laura", 6
                , ShipType.FISHING, dockA);
    }

    @Test
    @Transactional
    public void testChangeDock(){
        Dock dock1 = dockService.retrieveDock(1L);
        Dock dock2 = dockService.retrieveDock(2L);
        Ship ship1 = shipService.retrieveShip(1L);
        shipService.changeDock(ship1, dock2.getId());
        Long idB = ship.getDock().getId();
        Assertions.assertEquals(dock2.getId(), idB);
        Assertions.assertEquals(dock1.getOccupied(), 293);
        Assertions.assertEquals(dock2.getOccupied(), 207);
    }
}
