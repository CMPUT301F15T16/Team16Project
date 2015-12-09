package com.loveboyuan.smarttrader;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by boyuangu on 2015-11-05.
 */
public class TradeTest extends TestCase {

    public void testAcceptTrade() throws Exception {
        User borrower = new User(1);
        User owner = new User(2);
        Inventory bInventory = new Inventory();
        bInventory.setInventoryId(borrower.getMy_id());

        Inventory oInventory = new Inventory();
        oInventory.setInventoryId(owner.getMy_id());

        bInventory.addItem(new Item("Alogorthm", "Computer Science", 1, "New", Boolean.FALSE, "good book", ""));
        bInventory.addItem(new Item("benju", "French", 1, "Lightly Used", Boolean.FALSE, "language book", ""));
        oInventory.addItem(new Item("calculus", "Mathematics", 1, "Old", Boolean.FALSE, "nice book", ""));

        Item oitem = oInventory.getInventory().get(0);

        ArrayList<Item> bitems = new ArrayList<Item>();
        bitems.add(bInventory.getInventory().get(0));
        bitems.add(bInventory.getInventory().get(1));

        Trade trade = new Trade(owner.getMy_id(), oitem, borrower.getMy_id(), bitems);

        TradeHistory bTradeHistory = new TradeHistory();

        TradeHistory oTradeHistory = new TradeHistory();


        bTradeHistory.addTrade(trade);

        oTradeHistory.getTrades().get(0).setTradeResult(true);

        assertTrue(bTradeHistory.getTrades().get(0).getTradeResult());
        assertTrue(oTradeHistory.getTrades().get(0).getTradeResult());

    }





    public void testRejectTrade() throws Exception {
        User borrower = new User(1);
        User owner = new User(2);
        Inventory bInventory = new Inventory();
        bInventory.setInventoryId(borrower.getMy_id());

        Inventory oInventory = new Inventory();
        oInventory.setInventoryId(owner.getMy_id());

        bInventory.addItem(new Item("Alogorthm", "Computer Science", 1, "New", Boolean.FALSE, "good book", ""));
        bInventory.addItem(new Item("benju", "French", 1, "Lightly Used", Boolean.FALSE, "language book", ""));
        oInventory.addItem(new Item("calculus", "Mathematics", 1, "Old", Boolean.FALSE, "nice book", ""));

        Item oitem = oInventory.getInventory().get(0);

        ArrayList<Item> bitems = new ArrayList<Item>();
        bitems.add(bInventory.getInventory().get(0));
        bitems.add(bInventory.getInventory().get(1));

        Trade trade = new Trade(owner.getMy_id(), oitem,borrower.getMy_id(),bitems);

        TradeHistory bTradeHistory = new TradeHistory();

        TradeHistory oTradeHistory = new TradeHistory();

        bTradeHistory.addTrade(trade);

        oTradeHistory.getTrades().get(0).rejectTrade();

        assertFalse(bTradeHistory.getTrades().get(0).getTradeResult());
        assertFalse(oTradeHistory.getTrades().get(0).getTradeResult());

    }





    public void testMakeCounterTrader() throws Exception {
        User borrower = new User(1);
        User owner = new User(2);

        Inventory bInventory = new Inventory();
        bInventory.setInventoryId(borrower.getMy_id());

        Inventory oInventory = new Inventory();
        oInventory.setInventoryId(owner.getMy_id());
        bInventory.addItem(new Item("Alogorthm", "Computer Science", 1, "New", Boolean.FALSE, "good book", ""));
        bInventory.addItem(new Item("benju", "French", 1, "Lightly Used", Boolean.FALSE, "language book", ""));
        oInventory.addItem(new Item("calculus", "Mathematics", 1, "Old", Boolean.FALSE, "nice book", ""));

        Item oitem = oInventory.getInventory().get(0);

        ArrayList<Item> bitems = new ArrayList<Item>();
        bitems.add(bInventory.getInventory().get(0));
        bitems.add(bInventory.getInventory().get(1));

        Trade trade = new Trade(owner.getMy_id(), oitem,borrower.getMy_id(),bitems);

        TradeHistory bTradeHistory = new TradeHistory();

        TradeHistory oTradeHistory = new TradeHistory();

        bTradeHistory.addTrade(trade);

        oTradeHistory.getTrades().get(0).rejectTrade();

        assertFalse(bTradeHistory.getTrades().get(0).getTradeResult());
        assertFalse(oTradeHistory.getTrades().get(0).getTradeResult());

        Trade newtrade = oTradeHistory.getTrades().get(0).makeCounterTrader(trade);

        oTradeHistory.addTrade(newtrade);

        ArrayList<Item> oitems = new ArrayList<Item>();
        oitems.add(oitem);
        assertTrue(bTradeHistory.getTrades().get(0).getBItems().equals(oitems));
    }



}