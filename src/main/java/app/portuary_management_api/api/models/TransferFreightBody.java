package app.portuary_management_api.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferFreightBody {

    private Long freightId;
    private Long shipId;
    private Long dockId;
    private Integer quantity;
    private boolean fromShip;

}
