package com.loveboyuan.smarttrader;

import java.io.Serializable;
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

    public static void removeSerializableItem(Serializable myItem) {
        // I need to remove the real object, not just the serializable!
        Item sItem = (Item) myItem;

        for(Item item: getInventoryModel().getInventory()) {
            if (item.getName().equals(sItem.getClass().getName())) {
                getInventoryModel().removeItem(item);
            }
        }
    }
}
