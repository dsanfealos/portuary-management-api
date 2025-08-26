package api.portuary_management_api.service;

import api.portuary_management_api.entities.Dock;
import api.portuary_management_api.entities.Ship;
import api.portuary_management_api.entities.util.ShipType;
import api.portuary_management_api.services.DockService;
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
public class DockServiceTest {
    private Ship ship;
    private Dock dockA;
    private Dock dockB;

    @Autowired
    private DockService dockService;

    @BeforeEach
    public void init(){
        dockA = new Dock(2L, "Puerto del Este", "Valencia", 2500, 150
                , Collections.emptyList(), Collections.emptyList());
        dockB = new Dock(3L, "Puerto del Norte", "Bilbao", 3000, 500
                , Collections.emptyList(), Collections.emptyList());
        ship = new Ship(4L, "Merluza", "Laura", 6
                , ShipType.FISHING,dockA);
    }

    @Test
    @Transactional
    public void testCheckBerths(){
        int expectedBerths = 3;
        Assertions.assertEquals(expectedBerths, dockService.checkBerths(ship));
    }

    @Test
    @Transactional
    public void testShipEntersShipExits(){
        dockService.shipExits(ship);
        Assertions.assertEquals(dockA.getOccupied(), 147);
        dockService.shipEnters(ship);
        Assertions.assertEquals(dockA.getOccupied(), 150);
    }
}
