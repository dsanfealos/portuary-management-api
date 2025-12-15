package app.portuary_management_api.service;

import app.portuary_management_api.api.models.TransferFreightBody;
import app.portuary_management_api.entities.Freight;
import app.portuary_management_api.services.DockService;
import app.portuary_management_api.services.FreightService;
import app.portuary_management_api.services.ShipService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@AutoConfigureMockMvc
public class FreightServiceTest {

    @Autowired
    private DockService dockService;

    @Autowired
    private ShipService shipService;

    @Autowired
    private FreightService freightService;

    @BeforeEach
    public void init(){

    }

    @Test
    @Transactional
    public void transferPartialFreight_Test_ShipToDockExistingFreight(){
        TransferFreightBody body = new TransferFreightBody(2L, 2L, 1L, 10, true);

        Freight resultFreight = freightService.transferPartialFreight(body);
        Integer expectedDockQuantity = 500+10;
        Integer expectedShipQuantity = 29-10;
        Freight shipFinalFreight = freightService.retrieve(2L);
        Freight dockFinalFreight = freightService.retrieve(1L);

        Assertions.assertEquals(expectedDockQuantity, dockFinalFreight.getQuantity());
        Assertions.assertEquals(expectedShipQuantity, shipFinalFreight.getQuantity());
        Assertions.assertEquals(dockFinalFreight.getId(), resultFreight.getId());
    }

    @Test
    @Transactional
    public void transferPartialFreight_Test_DockToShipExistingFreight(){
        TransferFreightBody body = new TransferFreightBody(1L, 1L, 1L, 10, false);

        Freight resultFreight = freightService.transferPartialFreight(body);
        Integer expectedDockQuantity = 500-10;
        Integer expectedShipQuantity = 29+10;
        Freight shipFinalFreight = freightService.retrieve(2L);
        Freight dockFinalFreight = freightService.retrieve(1L);

        Assertions.assertEquals(expectedDockQuantity, dockFinalFreight.getQuantity());
        Assertions.assertEquals(expectedShipQuantity, shipFinalFreight.getQuantity());
        Assertions.assertEquals(shipFinalFreight.getId(), resultFreight.getId());
    }

    @Test
    @Transactional
    public void transferPartialFreight_Test_ShipToDockNotExistingFreight(){
        TransferFreightBody body = new TransferFreightBody(3L, 2L, 1L, 10, true);

        Freight resultFreight = freightService.transferPartialFreight(body);
        Integer expectedDockQuantity = 10;
        Integer expectedShipQuantity = 53-10;
        Freight shipFinalFreight = freightService.retrieve(3L);
        Freight dockFinalFreight = freightService.retrieve(4L);

        Assertions.assertEquals(expectedDockQuantity, dockFinalFreight.getQuantity());
        Assertions.assertEquals(expectedShipQuantity, shipFinalFreight.getQuantity());
        Assertions.assertEquals(dockFinalFreight.getId(), resultFreight.getId());
    }
}
