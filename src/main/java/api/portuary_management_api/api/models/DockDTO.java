package api.portuary_management_api.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DockDTO {

    private String name;
    private String city;
    private Integer capacity;
    private Integer occupied;
}
