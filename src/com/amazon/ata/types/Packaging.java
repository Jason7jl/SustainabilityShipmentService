package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a packaging option.
 *
 * This packaging supports standard boxes, having a length, width, and height.
 * Items can fit in the packaging so long as their dimensions are all smaller than
 * the packaging's dimensions.
 */
     public abstract class Packaging {
    /**
     * The material this packaging is made of.
     */
    private Material material;

    /**
     * Instantiates a new Packaging object.
     * @param material - the Material of the package
     */
    public Packaging(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    /**
     * Returns whether the given item will fit in this packaging.
     *
     * @param item the item to test fit for
     * @return whether the item will fit in this packaging
     */
    public abstract boolean canFitItem(Item item);
    /**
     * Returns the mass of the packaging in grams. The packaging weighs 1 gram per square centimeter.
     * @return the mass of the packaging
     */
    public abstract BigDecimal getMass();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Packaging packaging = (Packaging) o;
        return getMaterial() == packaging.getMaterial();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaterial());
    }
}
