package com.amazon.ata.cost;

import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;

/**
 * A strategy to calculate the cost of a ShipmentOption based on Coststrategy.
 */
public class WeightedCostStrategy implements CostStrategy {

    private MonetaryCostStrategy monetaryCostStrategy = new MonetaryCostStrategy();
    private CarbonCostStrategy carbonCostStrategy = new CarbonCostStrategy();

    public WeightedCostStrategy(){}
    /**
     * Initializes a WeightedCostStrategy.
     */
    public WeightedCostStrategy(MonetaryCostStrategy monetaryCostStrategy, CarbonCostStrategy carbonCostStrategy) {
        this.monetaryCostStrategy = monetaryCostStrategy;
        this.carbonCostStrategy = carbonCostStrategy;
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {

        BigDecimal monetaryCost = monetaryCostStrategy.getCost(shipmentOption).getCost().multiply(BigDecimal.valueOf(.80));
        BigDecimal carbonCost = carbonCostStrategy.getCost(shipmentOption).getCost().multiply(BigDecimal.valueOf(.20));

        BigDecimal totalCost = BigDecimal.valueOf(monetaryCost.doubleValue() + carbonCost.doubleValue());

        return new ShipmentCost(shipmentOption, totalCost);
    }
}

