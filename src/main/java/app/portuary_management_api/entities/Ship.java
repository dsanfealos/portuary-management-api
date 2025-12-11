package app.portuary_management_api.entities;

import app.portuary_management_api.entities.util.ShipSize;
import app.portuary_management_api.entities.util.ShipType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @JsonIgnore
    @OneToMany(mappedBy = "ship", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Freight> freights;

    public ShipSize howBig(){
        if(crewshipMembers < 5) return ShipSize.SMALL;
        if(crewshipMembers < 10) return ShipSize.REGULAR;
        if(crewshipMembers < 25) return ShipSize.BIG;
        return ShipSize.HUGE;
    }

    public boolean leaveDock(){
        this.dock = null;
        return true;
    }
}
