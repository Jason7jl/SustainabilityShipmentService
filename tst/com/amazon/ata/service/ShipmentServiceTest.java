package com.amazon.ata.service;

import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ShipmentServiceTest {

    private Item smallItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1))
            .withWidth(BigDecimal.valueOf(1))
            .withLength(BigDecimal.valueOf(1))
            .withAsin("abcde")
            .build();

    private Item largeItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1000))
            .withWidth(BigDecimal.valueOf(1000))
            .withLength(BigDecimal.valueOf(1000))
            .withAsin("12345")
            .build();

    private FulfillmentCenter existentFC = new FulfillmentCenter("ABE2");
    private FulfillmentCenter nonExistentFC = new FulfillmentCenter("NonExistentFC");
    private Packaging packaging = new PolyBag(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(100));
    @InjectMocks
    private ShipmentService shipmentService;

    @Mock PackagingDAO packagingDAO;
    @Mock MonetaryCostStrategy monetaryCostStrategy;

    @BeforeEach
    void setUp() {openMocks(this);}



    @Test
    void findBestShipmentOption_nonexistentFCAndItemCanFit_returnsShipmentOption()
            throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
       //GIVEN
            shipmentService = new ShipmentService(packagingDAO, new MonetaryCostStrategy());

            ShipmentOption shipmentOption = ShipmentOption.builder()
                    .withItem(smallItem)
                    .withPackaging(packaging)
                    .withFulfillmentCenter(existentFC)
                    .build();

            List<ShipmentOption> shipmentOptionList = new ArrayList<>();
            shipmentOptionList.add(shipmentOption);

            when(packagingDAO.findShipmentOptions(smallItem, existentFC)).thenReturn(shipmentOptionList);
            // WHEN
            ShipmentOption shipmentOptionUnderTest = shipmentService.findShipmentOption(smallItem, existentFC);

            // THEN
            assertEquals(shipmentOptionUnderTest.getClass(), ShipmentOption.class, "The method should return  " +
                    "a ShipmentOption object");

    }

    @Test
    void findBestShipmentOption_existentFCAndItemCannotFit_returnsShipmentOptionWithNullPackaging()
            throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {

                shipmentService = new ShipmentService(packagingDAO, new MonetaryCostStrategy());
                ShipmentOption shipmentOption = ShipmentOption.builder()
                                .withItem(largeItem)
                                .withPackaging(null)
                                .withFulfillmentCenter(existentFC)
                                .build();

        when(packagingDAO.findShipmentOptions(largeItem, existentFC))
                .thenThrow(new NoPackagingFitsItemException());
        // WHEN
        ShipmentOption shipmentOptionUnderTest = shipmentService.findShipmentOption(largeItem, existentFC);

        // THEN
        assertEquals(shipmentOptionUnderTest.getItem(), shipmentOption.getItem(), "The method should return  " +
                "a valid ShipmentOption with appropriate Item");
        assertEquals(shipmentOptionUnderTest.getClass(), shipmentOption.getClass(), "The method should return  " +
                "a valid ShipmentOption");
        assertEquals(shipmentOptionUnderTest.getFulfillmentCenter(), shipmentOption.getFulfillmentCenter()
                , "The method should return, a valid ShipmentOption with appropriate FC");
        assertNull(shipmentOptionUnderTest.getPackaging(), "The method should return null ");

    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCanFit_returnsShipmentOption()
            throws UnknownFulfillmentCenterException, NoPackagingFitsItemException{
        // GIVEN
        shipmentService = new ShipmentService(packagingDAO, new MonetaryCostStrategy());
        when(packagingDAO.findShipmentOptions(smallItem, existentFC))
                .thenThrow(new UnknownFulfillmentCenterException());

        //
        // WHEN & THEN
        assertThrows(RuntimeException.class, () ->{
            shipmentService.findShipmentOption(smallItem, nonExistentFC);
        }, "WHEN NOFC EXISTS, A RUNTIME SHOULD BE THROWN");
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCannotFit_returnsShipmentOption()
            throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        shipmentService = new ShipmentService(packagingDAO, new MonetaryCostStrategy());
        when(packagingDAO.findShipmentOptions(largeItem, existentFC))
                .thenThrow(new UnknownFulfillmentCenterException());

        //
        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(largeItem, nonExistentFC);
        }, "WHEN NO FC EXISTS and the item cannot fit, A RUNTIME SHOULD BE THROWN");
    }
}