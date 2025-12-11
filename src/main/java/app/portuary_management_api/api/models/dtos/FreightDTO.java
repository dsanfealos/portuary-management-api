package app.portuary_management_api.api.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FreightDTO {
    private String type;
    private Integer quantity;
    private Long dockId;
    private Long shipId;

}
