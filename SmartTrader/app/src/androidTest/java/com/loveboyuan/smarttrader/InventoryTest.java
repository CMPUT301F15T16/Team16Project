package com.loveboyuan.smarttrader;

import junit.framework.TestCase;

import java.lang.Object;
import java.util.ArrayList;



public class InventoryTest extends TestCase {

    Inventory tester;
    Item item = new Item("Tester", "Computer Science", 1, "New", false, "A tester for the app.", null);

    public void testSetup() {
        tester = new Inventory();
    }

    public void addItem(Item item) {
        tester.addItem(item);

        int size = tester.getInventory().size();
        if (size == 1) {
            assert (true);
        } else {
            assert (false);
        }
    }

    public void removeItem(Item item) {
        tester.removeItem(item);

        int size = tester.getInventory().size();
        if (size == 0) {
            assert (true);
        } else {
            assert (false);

        }
    }
}