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
    @Column(name = "name")
    private String name;
    @Column(name = "captain")
    private String captain;
    @Column(name = "crewship_members")
    private Integer crewshipMembers;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ShipType type;
    @JoinColumn(name = "dock")
    @ManyToOne(optional = false)
    private Dock dock;

    public Ship(String name, String captain, Integer crewshipMembers, ShipType type, Dock dock) {
        this.name = name;
        this.captain = captain;
        this.crewshipMembers = crewshipMembers;
        this.type = type;
        this.dock = dock;
    }

    public ShipSize howBig(){
        if(crewshipMembers < 5) return ShipSize.SMALL;
        if(crewshipMembers < 10) return ShipSize.REGULAR;
        if(crewshipMembers < 25) return ShipSize.BIG;
        return ShipSize.HUGE;
    }
}
