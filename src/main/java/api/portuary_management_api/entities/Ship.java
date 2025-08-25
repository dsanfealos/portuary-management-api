package api.portuary_management_api.entities;

import api.portuary_management_api.entities.util.ShipSize;
import api.portuary_management_api.entities.util.ShipType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String captain;
    private Integer crewshipMembers;
    @Enumerated(EnumType.STRING)
    private ShipType type;
    @ManyToOne(optional = false)
    private Dock dock;

    public ShipSize howBig(){
        if(crewshipMembers < 5) return ShipSize.SMALL;
        if(crewshipMembers < 10) return ShipSize.REGULAR;
        if(crewshipMembers < 25) return ShipSize.BIG;
        return ShipSize.HUGE;
    }
}
