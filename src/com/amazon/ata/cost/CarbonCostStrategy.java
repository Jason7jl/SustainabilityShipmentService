package com.amazon.ata.cost;

import com.amazon.ata.cost.CostStrategy;
import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * A strategy to calculate the carbon cost in "carbon units" (cu) of a ShipmentOption.
 */
public class CarbonCostStrategy implements CostStrategy {

    private final Map<Material, BigDecimal> carbonUnitsPerGram;

    public CarbonCostStrategy() {
        carbonUnitsPerGram = new HashMap<>();
        carbonUnitsPerGram.put(Material.CORRUGATE, BigDecimal.valueOf(.017));
        carbonUnitsPerGram.put(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(.012));
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        BigDecimal environmentalCost = this.carbonUnitsPerGram.get(packaging.getMaterial());

        BigDecimal cost = packaging.getMass().multiply(environmentalCost);

        return new ShipmentCost(shipmentOption, cost);
    }
}

