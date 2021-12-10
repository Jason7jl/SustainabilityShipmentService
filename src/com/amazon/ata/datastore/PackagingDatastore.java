package com.amazon.ata.datastore;

import com.amazon.ata.types.Box;
import com.amazon.ata.types.FcPackagingOption;
import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.PolyBag;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Stores all configured packaging pairs for all fulfillment centers.
 */
public class PackagingDatastore {

    /**
     * The stored pairs of fulfillment centers to the packaging options they support.
     */
    private final List<FcPackagingOption> fcPackagingOptions = Arrays.asList(
            createFcPackagingBox("IND1", Material.CORRUGATE, "10", "10", "10"),
            createFcPackagingBox("ABE2", Material.CORRUGATE, "20", "20", "20"),
            createFcPackagingBox("ABE2", Material.CORRUGATE, "40", "40", "40"),
            createFcPackagingBox("YOW4", Material.CORRUGATE, "10", "10", "10"),
            createFcPackagingBox("YOW4", Material.CORRUGATE, "20", "20", "20"),
            createFcPackagingBox("YOW4", Material.CORRUGATE, "60", "60", "60"),
            createFcPackagingBox("IAD2", Material.CORRUGATE, "20", "20", "20"),
            createFcPackagingBox("IAD2", Material.CORRUGATE, "20", "20", "20"),
            createFcPackagingBox("PDX1", Material.CORRUGATE, "40", "40", "40"),
            createFcPackagingBox("PDX1", Material.CORRUGATE, "60", "60", "60"),
            createFcPackagingBox("PDX1", Material.CORRUGATE, "60", "60", "60"),
            createFcPackagingPolyBag("IAD2", Material.LAMINATED_PLASTIC, "2000"),
            createFcPackagingPolyBag("IAD2", Material.LAMINATED_PLASTIC, "10000")
    );


    /**
     * Create fulfillment center packaging option from provided parameters.
     */
    private FcPackagingOption createFcPackagingBox(String fcCode, Material material,
                                                      String length, String width, String height) {
        // we create instance fulfilment service and pass param. fcCode
        FulfillmentCenter fulfillmentCenter = new FulfillmentCenter(fcCode);
        Packaging packaging = new Box(material, new BigDecimal(length), new BigDecimal(width),
                new BigDecimal(height));

        return new FcPackagingOption(fulfillmentCenter, packaging);
    }

    /**
     *
     * @param fcCode - a fcCode
     * @param material - a material
     * @param volume - a volume
     * @return - a return
     */
    private FcPackagingOption createFcPackagingPolyBag(String fcCode, Material material, String volume) {
        // we create instance fulfilment service and pass param. fcCode
        FulfillmentCenter fulfillmentCenter = new FulfillmentCenter(fcCode);
        Packaging packaging = new PolyBag(material, new BigDecimal(volume));

        return new FcPackagingOption(fulfillmentCenter, packaging);
    }



    public List<FcPackagingOption> getFcPackagingOptions() {
        return fcPackagingOptions;
    }
}
