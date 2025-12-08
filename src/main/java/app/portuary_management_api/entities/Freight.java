package app.portuary_management_api.entities;

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
public class Freight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Integer quantity;
    @ManyToOne(optional = false)
    private Dock dock;

    public void addQuantity(int extraQuantity){
        this.quantity = this.quantity + extraQuantity;
    }

    public void substractQuantity(int substractedQuantity){
        this.quantity = this.quantity - substractedQuantity;
    }

}
