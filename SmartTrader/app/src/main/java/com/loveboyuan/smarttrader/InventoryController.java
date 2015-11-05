package com.loveboyuan.smarttrader;

import java.util.ArrayList;

/**
 * Created by boyuangu on 2015-11-02.
 */
public class InventoryController {
    private static Inventory inventory = null;

    static public Inventory getInventoryModel() {
        if (inventory == null) {
            inventory = new Inventory();
            return inventory;

        } else {
            return inventory;

        }

    }

    public static void addItem(Item item) {
            getInventoryModel().addItem(item);


    }

    public static void removeItem(Item item) {
        getInventoryModel().removeItem(item);
    }

}
