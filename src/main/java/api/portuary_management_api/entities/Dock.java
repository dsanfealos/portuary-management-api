package api.portuary_management_api.entities;

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
public class Dock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private Integer capacity;
    private Integer occupied;
    @JsonIgnore
    @OneToMany(mappedBy = "dock", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Ship> ships;
    @JsonIgnore
    @OneToMany(mappedBy = "docks", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Freight> freights;


    public boolean isFull(){
        return occupied >= capacity;
    }

    public void increaseOccupied(int berths){
        this.occupied = this.occupied + berths;
    }

    public void decreaseOccupied(int berths){
        this.occupied = this.occupied - berths;
    }

}
