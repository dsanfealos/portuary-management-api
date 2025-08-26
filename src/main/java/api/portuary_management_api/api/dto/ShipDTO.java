package api.portuary_management_api.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipDTO {

    private String name;
    private String captain;
    private Integer crewshipMembers;
    private String type;
    private Long dockId;
}
