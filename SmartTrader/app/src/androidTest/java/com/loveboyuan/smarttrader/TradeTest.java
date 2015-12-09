package com.loveboyuan.smarttrader;  import junit.framework.TestCase;  import java.util.ArrayList;  
        /**  * Created by boyuangu on 2015-11-05. */

         public class TradeTest extends TestCase {
      

    public void testAcceptTrade() throws Exception { 
        User borrower = new User(1); 
        User owner = new User(2);   
        borrower.getInventory().addItem(new Item("Alogorthm", "Computer Science", 1, "New", Boolean.FALSE, "good book", "")); 
        borrower.getInventory().addItem(new Item("benju", "French", 1, "Lightly Used", Boolean.FALSE, "language book", "")); 
        owner.getInventory().addItem(new Item("calculus", "Mathematics", 1, "Old", Boolean.FALSE, "nice book", ""));

          Item oitem = owner.getInventory().getInventory().get(0);  
        ArrayList<Item> bitems = new ArrayList<Item>(); 
        bitems.add(borrower.getInventory().getInventory().get(0)); 
        bitems.add(borrower.getInventory().getInventory().get(1));  
        Trade trade = new Trade(owner, oitem, borrower, bitems); 
        borrower.proposeTrade(trade);  
        owner.getTradeHistory().getTrade(trade).acceptTrade();  
        assertTrue(borrower.getTradeHistory().getTrade(trade).getTradeResult()); 
        assertTrue(owner.getTradeHistory().getTrade(trade).getTradeResult()); 
        assertTrue(owner.getTradeHistory.getTrade(trade).getTradeState.equals("In-progress")); 
        asserttrue(borrower.getTradeHistory.getTrade(trade).getTradeState.equals("In-progress"));   

        // Now the Borrower returns the Item to the Owner 
        borrower.getInventory().addItem(new Item("Alogorthm", "Computer Science", 1, "New", Boolean.FALSE, "good book", "")); 
        borrower.getInventory().addItem(new Item("benju", "French", 1, "Lightly Used", Boolean.FALSE, "language book", "")); 
        owner.getInventory().addItem(new Item("calculus", "Mathematics", 1, "Old", Boolean.FALSE, "nice book", ""));  
        Item bitem = borrower.getInventory().getInventory().get(0);  
        ArrayList<Item> oitems = new ArrayList<Item>(); 
        oitems.add(owner.getInventory().getInventory().get(0)); 
        oitems.add(owner.getInventory().getInventory().get(1));  
        Trade trade = new Trade(borrower, bitem, owner, oitems); 
        owner.proposeTrade(trade);  
        borrower.getTradeHistory().getTrade(trade).acceptTrade();  
        assertTrue(owner.getTradeHistory().getTrade(trade).getTradeResult()); 
        assertTrue(borrower.getTradeHistory().getTrade(trade).getTradeResult()); 
        assertTrue(borrower.getTradeHistory.getTrade(trade).getTradeState.equals("Complete")); 
        asserttrue(owner.getTradeHistory.getTrade(trade).getTradeState.equals("Complete"));    
    }

      

    public void testRejectTrade() throws Exception { 
        User borrower = new User(1); 
        User owner = new User(2);   
        borrower.getInventory().addItem(new Item("Alogorthm", "Computer Science", 1, "New", Boolean.FALSE, "good book", "")); 
        borrower.getInventory().addItem(new Item("benju", "French", 1, "Lightly Used", Boolean.FALSE, "language book", "")); 
        owner.getInventory().addItem(new Item("calculus", "Mathematics", 1, "Old", Boolean.FALSE, "nice book", ""));  
        Item oitem = owner.getInventory().getInventory().get(0);  
        ArrayList<Item> bitems = new ArrayList<Item>(); 
        bitems.add(borrower.getInventory().getInventory().get(0)); 
        bitems.add(borrower.getInventory().getInventory().get(1));  
        Trade trade = new Trade(owner, oitem, borrower, bitems); 
        borrower.proposeTrade(trade);  
        owner.getTradeHistory().getTrade(trade).rejectTrade();  
        assertFalse(borrower.getTradeHistory().getTrade(trade).getTradeResult()); 
        assertFalse(owner.getTradeHistory().getTrade(trade).getTradeResult());  
    }

      

    public void testMakeCounterTrader() throws Exception { 
        User borrower = new User(1); 
        User owner = new User(2);   
        borrower.getInventory().addItem(new Item("Alogorthm", "Computer Science", 1, "New", Boolean.FALSE, "good book", "")); 
        borrower.getInventory().addItem(new Item("benju", "French", 1, "Lightly Used", Boolean.FALSE, "language book", "")); 
        owner.getInventory().addItem(new Item("calculus", "Mathematics", 1, "Old", Boolean.FALSE, "nice book", ""));  
        Item oitem = owner.getInventory().getInventory().get(0);  
        ArrayList<Item> bitems = new ArrayList<Item>(); 
        bitems.add(borrower.getInventory().getInventory().get(0)); 
        bitems.add(borrower.getInventory().getInventory().get(1));  
        Trade trade = new Trade(owner, oitem, borrower, bitems); 
        borrower.proposeTrade(trade);  
        owner.getTradeHistory().getTrade(trade).rejectTrade();  
        assertFalse(borrower.getTradeHistory().getTrade(trade).getTradeResult()); 
        assertFalse(owner.getTradeHistory().getTrade(trade).getTradeResult());  
        Trade newtrade = owner.getTradeHistory().getTrade(trade).makeCounterTrader();  
        owner.proposeTrade(newtrade);  
        ArrayList<Item> oitems = new ArrayList<Item>(); 
        oitems.add(oitem); 
        assertTrue(borrower.getTradeHistory().getTrade(newtrade).getBItems().equals(oitems)); 
    }

      

    public void testEditOItem() throws Exception { 
        User borrower = new User(1); 
        User owner = new User(2);   
        borrower.getInventory().addItem(new Item("Alogorthm", "Computer Science", 1, "New", Boolean.FALSE, "good book", "")); 
        borrower.getInventory().addItem(new Item("benju", "French", 1, "Lightly Used", Boolean.FALSE, "language book", "")); 
        owner.getInventory().addItem(new Item("calculus", "Mathematics", 1, "Old", Boolean.FALSE, "nice book", ""));  
        Item oitem = owner.getInventory().getInventory().get(0);  
        ArrayList<Item> bitems = new ArrayList<Item>(); 
        bitems.add(borrower.getInventory().getInventory().get(0)); 
        bitems.add(borrower.getInventory().getInventory().get(1));  
        Trade trade = new Trade(owner, oitem, borrower, bitems); 
        borrower.proposeTrade(trade);  
        Item newOItem = new Item("number theory", "Mathematics", 1, "Old", Boolean.FALSE, "a nice book", ""); 
        owner.getInventory().addItem(newOItem); 
        borrower.getTradeHistory().getTrade(trade).editOItem(owner.getInventory().getInventory().get(1)); 
        assertTrue(owner.getTradeHistory().getTrade(trade).getOItem().equals(newOItem)); 
    }

      

    public void testEditBItems() throws Exception { 
        User borrower = new User(1); 
        User owner = new User(2);   
        Item item1 = new Item("Alogorthm", "Computer Science", 1, "New", Boolean.FALSE, "good book", ""); 
        borrower.getInventory().addItem(item1); 
        borrower.getInventory().addItem(new Item("benju", "French", 1, "Lightly Used", Boolean.FALSE, "language book", "")); 
        owner.getInventory().addItem(new Item("calculus", "Mathematics", 1, "Old", Boolean.FALSE, "nice book", ""));  
        Item oitem = owner.getInventory().getInventory().get(0);  
        ArrayList<Item> bitems = new ArrayList<Item>(); 
        bitems.add(borrower.getInventory().getInventory().get(0)); 
        bitems.add(borrower.getInventory().getInventory().get(1));  
        Trade trade = new Trade(owner, oitem, borrower, bitems); 
        borrower.proposeTrade(trade);  
        ArrayList<Item> newBItems = new ArrayList<Item>(); 
        newBItems = bitems; 
        newBItems.remove(item1); 
        borrower.getTradeHistory().getTrade(trade).editBItems(newBItems);  
        owner.getTradeHistory().getTrade(trade).getBItems().equals(newBItems);  

    }
}
