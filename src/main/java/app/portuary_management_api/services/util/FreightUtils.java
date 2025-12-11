package app.portuary_management_api.services.util;

import app.portuary_management_api.entities.Freight;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FreightUtils {

    public static Integer howManyItemsOfFreight(String freightType, List<Freight> freights){
        Optional<Integer> quantity = freights.stream()
                .filter(freight -> Objects.equals(freight.getType(), freightType))
                .map(Freight::getQuantity)
                .reduce(Integer::sum);
        return quantity.orElse(null);
    }

    public static Integer howManyDifferentFreightsOfType(String freightType, List<Freight> freights){
        long quantity = freights.stream()
                .filter(freight -> Objects.equals(freight.getType(), freightType))
                .count();
        return (int) quantity;
    }
}
