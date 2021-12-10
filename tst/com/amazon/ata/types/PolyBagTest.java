package com.amazon.ata.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolyBagTest {
    private Material packagingMaterial = Material.LAMINATED_PLASTIC;
    private BigDecimal packagingLength = BigDecimal.valueOf(5.6);
    private BigDecimal packagingWidth = BigDecimal.valueOf(3.3);
    private BigDecimal packagingHeight = BigDecimal.valueOf(8.1);
    private BigDecimal packagingVolume = BigDecimal.valueOf(149.688);

    private Packaging packaging;

    @BeforeEach
    public void setUp() {
        packaging = new PolyBag(packagingMaterial, packagingVolume);
                //Box(packagingMaterial, packagingLength, packagingWidth, packagingHeight);
    }

    @Test
    public void canFitItem_itemLengthTooLong_doesNotFit() {
        // GIVEN
        //item.getHeight().multiply(item.getLength().multiply(item.getWidth()))
        Item item = Item.builder()
                .withLength(packagingLength)
                .withWidth(packagingWidth)
                .withHeight(packagingHeight)
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with longer length than package should not fit in the package.");
    }

    @Test
    public void canFitItem_itemWidthTooLong_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(packagingLength)
                .withWidth(packagingWidth.add(BigDecimal.ONE))
                .withHeight(packagingHeight)
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with longer width than package should not fit in the package.");
    }

    @Test
    public void canFitItem_itemHeightTooLong_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(packagingLength)
                .withWidth(packagingWidth)
                .withHeight(packagingHeight.add(BigDecimal.ONE))
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with longer height than package should not fit in the package.");
    }

    @Test
    public void canFitItem_itemSameSizeAsPolyBag_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(packagingLength)
                .withWidth(packagingWidth)
                .withHeight(packagingHeight)
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item the same size as the package should not fit in the package.");
    }

    @Test
    public void canFitItem_itemSmallerThanPolyBox_doesFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(packagingLength.subtract(BigDecimal.ONE))
                .withWidth(packagingWidth.subtract(BigDecimal.ONE))
                .withHeight(packagingHeight.subtract(BigDecimal.ONE))
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertTrue(canFit, "Item smaller than the package should fit in the package.");
    }

    @Test
    public void getMass_calculatesMass_returnsCorrectMass() {
        // GIVEN
        packaging = new PolyBag(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(20));

        // WHEN
        BigDecimal mass = packaging.getMass();

        // THEN
        assertEquals(BigDecimal.valueOf(3.0), mass,
                "Item smaller than the polybag should fit in the package.");
    }

    @Test
    public void equals_sameObject_isTrue() {
        // GIVEN
        Packaging packaging = new PolyBag(Material.LAMINATED_PLASTIC, packagingVolume);

        // WHEN
        boolean result = packaging.equals(packaging);

        // THEN
        assertTrue(result, "An object should be equal with itself.");
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // GIVEN
        Packaging packaging = new PolyBag(Material.LAMINATED_PLASTIC, packagingVolume);

        // WHEN
        boolean isEqual = packaging.equals(null);

        // THEN
        assertFalse(isEqual, "A Packaging should not be equal with null.");
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        // GIVEN
        Packaging packaging = new PolyBag(Material.LAMINATED_PLASTIC, packagingVolume);
        Object other = "String type!";

        // WHEN
        boolean isEqual = packaging.equals(other);

        // THEN
        assertFalse(isEqual, "A Packaging should not be equal to an object of a different type.");
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        // GIVEN
        Packaging packaging = new PolyBag(Material.LAMINATED_PLASTIC, packagingVolume);
        Object other = new PolyBag(Material.LAMINATED_PLASTIC, packagingVolume);

        // WHEN
        boolean isEqual = packaging.equals(other);

        // THEN
        assertTrue(isEqual, "Packaging with the same attributes should be equal.");
    }

    @Test
    public void hashCode_equalObjects_equalHash() {
        // GIVEN
        Packaging packaging = new PolyBag(Material.LAMINATED_PLASTIC, packagingVolume);
        Packaging other = new PolyBag(Material.LAMINATED_PLASTIC, packagingVolume);

        // WHEN + THEN
        assertEquals(packaging.hashCode(), other.hashCode(), "Equal objects should have equal hashCodes");
    }
}

