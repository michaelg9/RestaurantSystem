/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author pbj
 *
 */
public class SystemTest extends TestBasis {   

    /*
     ***********************************************************************
     * BEGIN MODIFICATION AREA
     ***********************************************************************
     * Add all your JUnit tests for your system below.
     */
    
     
    @Test 
    public void cancelReadyUpLight() {
        //Checks the CancelReadyUpLight use case
        logger.info(makeBanner("cancelReadyUpLight"));
        input("1 18:00, PassButton, pb, press");
        expect("1 18:00, PassLight, pl, viewSwitchedOff");
        
        runAndCheck();
    }
    
    @Test
    public void showOfficeMenu(){
        //Tests the output of an empty Menu
        logger.info(makeBanner("showOfficeMenu"));
        input("1 19:00, OfficeKVM, okvm, showMenu"); 
        expect("1 19:00, OfficeKVM, okvm, viewMenu, tuples, 3, ID, Description, Price"); 
        
        runAndCheck();
    }
    
    private void populateMenu(){
        //Populates the Menu with some default items
        input("1 18:00, OfficeKVM, okvm, addToMenu, D1, Bottled Water, 1.00");
        input("1 18:00, OfficeKVM, okvm, addToMenu, D2, Beer, 4.50");
        input("1 18:00, OfficeKVM, okvm, addToMenu, M1, Cheese burger, 9.95");
        input("1 18:00, OfficeKVM, okvm, addToMenu, M2, Ceasar salad, 7.90");
    }
    
    @Test
    public void addToMenu(){
        //Check Basic addition to Menu 
        logger.info(makeBanner("addToMenu"));
        populateMenu();
        input("1 19:00, OfficeKVM, okvm, showMenu"); 
        expect("1 19:00, OfficeKVM, okvm, viewMenu, tuples, 3, ID, Description, Price, "
                + "D1, Bottled Water, 1.00, "
                + "D2, Beer, 4.50, "
                + "M1, Cheese burger, 9.95, "
                + "M2, Ceasar salad, 7.90"); 
        
        runAndCheck();
    }

    @Test
    public void removeFromMenu(){
        //Check removal of an item from the Menu
        logger.info(makeBanner("removeFromMenu"));
        populateMenu();
        input("1 18:00, OfficeKVM, okvm, removeFromMenu, D2");
        input("1 19:00, OfficeKVM, okvm, showMenu"); 
        expect("1 19:00, OfficeKVM, okvm, viewMenu, tuples, 3, ID, Description, Price, "
                + "D1, Bottled Water, 1.00, "
                + "M1, Cheese burger, 9.95, "
                + "M2, Ceasar salad, 7.90"); 
        
        runAndCheck();
    }
    @Test
    public void addToMenuOrderCheck(){
        //Check if the ordering of the Menu items is correct
        logger.info(makeBanner("addToMenuOrderCheck"));
        input("1 18:00, OfficeKVM, okvm, addToMenu, Z1, Eggs, 2.50");
        input("1 18:00, OfficeKVM, okvm, addToMenu, D2, Ouzo, 5.00");
        input("1 18:00, OfficeKVM, okvm, addToMenu, W1, Halloumi Burger, 3.50");
        input("1 18:00, OfficeKVM, okvm, addToMenu, A1, Souvlaki, 8.00");
        input("1 19:00, OfficeKVM, okvm, showMenu"); 
        expect("1 19:00, OfficeKVM, okvm, viewMenu, tuples, 3, ID, Description, Price, "
                + "A1, Souvlaki, 8.00, "
                + "D2, Ouzo, 5.00, "
                + "W1, Halloumi Burger, 3.50, "
                + "Z1, Eggs, 2.50"); 
        
        runAndCheck();
    }
    
    @Test
    public void addExistingItemCheck(){
        //Checks if an item with an already existing menu item id is updating the menu item
        logger.info(makeBanner("addExistingItemCheck"));
        populateMenu();
        input("1 18:30, OfficeKVM, okvm, addToMenu, D2, Soda, 2.50");
        input("1 19:00, OfficeKVM, okvm, showMenu");
        expect("1 19:00, OfficeKVM, okvm, viewMenu, tuples, 3, ID, Description, Price, "
                + "D1, Bottled Water, 1.00, "
                + "D2, Soda, 2.50, "
                + "M1, Cheese burger, 9.95, "
                + "M2, Ceasar salad, 7.90"); 
        
        runAndCheck();
    }
    
    @Test(expected = AssertionError.class)
    public void addNegativePriceItemCheck() throws AssertionError{
        //Checks if an adding item with a negative price is throwing an Assertion Error
        logger.info(makeBanner("addNegativePriceItemCheck"));
        input("1 18:30, OfficeKVM, okvm, addToMenu, S1, Waffles, -8.50");
        
        runAndCheck();
    }
    
    @Test(expected = AssertionError.class)
    public void removeNonexistingItemCheck() throws AssertionError{
        /*Check if an Assertion Error is thrown if there is 
         * an attempt to remove a nonexisting item to the Menu
         */
        logger.info(makeBanner("removeNonexistingItemCheck"));
        populateMenu();
        input("1 18:30, OfficeKVM, okvm, removeFromMenu, Z3");
        
        runAndCheck();
    }
    
    @Test
    public void addShowTicket(){
        /* General success scenario check of the showTicket use case
         * Checks if the items are ordered by their Id
         * Checks if multiple items of a single menu item added, 
         * increase the quantity of that item ordered accordingly
         */
        logger.info(makeBanner("addShowTicket"));
        input("1 12:00, OfficeKVM, okvm, addToMenu, M4, Rib eye Steak, 15.90");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M15, Pork Chops, 10.30");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D25, Bottled Water, 2.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D3, Ouzo, 5.00");
        input("1 12:00, OfficeKVM, okvm, addToMenu, Z3, Greek Yoghurt, 4.50");
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, M4");
        input("1 20:01, TableDisplay, td1, addMenuItem, M4");
        input("1 20:01, TableDisplay, td1, addMenuItem, M15");
        input("1 20:01, TableDisplay, td1, addMenuItem, D3");
        input("1 20:01, TableDisplay, td1, addMenuItem, Z3");
        input("1 20:01, TableDisplay, td1, showTicket");
        expect("1 20:01, TableDisplay, td1, viewTicket, tuples, 3, "
                + "ID, Description, Count, "
                + "D3, Ouzo, 1, "
                + "M15, Pork Chops, 1, "
                + "M4, Rib eye Steak, 2, "
                + "Z3, Greek Yoghurt, 1"); 
        
        runAndCheck();
    }
    
    @Test
    public void removeFromTicket(){
        //Checks basic removal of an item from the ticket, decrementing its quantity
        logger.info(makeBanner("removeFromTicket"));
        populateMenu();
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1"); // D1 Quantity:1
        input("1 20:01, TableDisplay, td1, addMenuItem, D1"); // D1 Quantity:2
        input("1 20:01, TableDisplay, td1, addMenuItem, D1"); // D1 Quantity:3
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M2");
        input("1 20:01, TableDisplay, td1, removeMenuItem, D1"); // D1--; D1 Quantity:2
        input("1 20:01, TableDisplay, td1, showTicket");
        expect("1 20:01, TableDisplay, td1, viewTicket, tuples, 3, "
                + "ID, Description, Count, "
                + "D1, Bottled Water, 2, "
                + "M1, Cheese burger, 1, "
                + "M2, Ceasar salad, 1"); 
        
        runAndCheck();
    }
    
    @Test
    public void removeItemFromTicket(){
        /* Checks that when a removal of an item from the ticket, 
         * makes the quantity of that item zero, the item is removed
         * 
         */
        logger.info(makeBanner("removeItemFromTicket"));
        populateMenu();
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1"); // D1 Quantity:1
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M2");
        input("1 20:01, TableDisplay, td1, removeMenuItem, D1"); // D1--; D1 Quantity:0
        input("1 20:01, TableDisplay, td1, showTicket");
        expect("1 20:01, TableDisplay, td1, viewTicket, tuples, 3, "
                + "ID, Description, Count, "
                + "M1, Cheese burger, 1, "
                + "M2, Ceasar salad, 1"); 
        
        runAndCheck();
    }
    
    @Test(expected = AssertionError.class)
    public void removeNotItemInOrder() throws AssertionError{
        /* Checks if an assertion error is thrown when there is an attempt 
         * to remove an item from the order ticket that is not inside
         * 
         */
        logger.info(makeBanner("removeNotItemInOrder"));
        populateMenu();
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1"); 
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M2");
        input("1 20:01, TableDisplay, td1, removeMenuItem, D2"); 
        //Item with menu id D2 is not in the order ticket
        
        runAndCheck();
    }
    
    
    @Test(expected = AssertionError.class)
    public void showMenuNoOrder() throws AssertionError{
        /* Checks if an assertion error is thrown when there is an
         * attempt to display the menu when an order has not
         * been initialised.
         * 
         */
        logger.info(makeBanner("showMenuNoOrder"));
        populateMenu();
        input("1 20:01, TableDisplay, td1, showMenu");
        
        runAndCheck();
    }
    
    @Test(expected = AssertionError.class)
    public void showTicketNoOrder() throws AssertionError{
        /* Checks if an assertion error is thrown when there is an
         * attempt to display an order ticket when an order has not
         * been initialised.
         * 
         */
        logger.info(makeBanner("showTicketNoOrder"));
        populateMenu();
        input("1 20:01, TableDisplay, td1, showTicket");
        
        runAndCheck();
    }
    
    @Test(expected = AssertionError.class)
    public void addNoOrder() throws AssertionError{
        /* Checks if an assertion error is thrown when there is an
         * attempt to add an item to an order when an order has not
         * been initialised.
         * 
         */
        logger.info(makeBanner("addNoOrder"));
        populateMenu();
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        
        runAndCheck();
    }
    
    @Test(expected = AssertionError.class)
    public void removeNoOrder() throws AssertionError{
        /* Checks if an assertion error is thrown when there is an
         * attempt to remove an item to an order when an order has not
         * been initialised.
         * 
         */
        logger.info(makeBanner("removeNoOrder"));
        populateMenu();
        input("1 20:01, TableDisplay, td1, removeMenuItem, D1");
        
        runAndCheck();
    }
    
    @Test(expected = AssertionError.class)
    public void submitNoOrder() throws AssertionError{
        /* Checks if an assertion error is thrown when there is an
         * attempt to submit an order when an order has not
         * been initialised.
         * 
         */
        logger.info(makeBanner("submitNoOrder"));
        populateMenu();
        input("1 20:01, TableDisplay, td1, submitOrder");
        
        runAndCheck();
    }
    
    @Test(expected = AssertionError.class)
    public void payBillNoOrder() throws AssertionError{
        /* Checks if an assertion error is thrown when there is an
         * attempt to pay the bill of an order when an order has not
         * been initialised.
         * 
         */
        logger.info(makeBanner("payBillNoOrder"));
        populateMenu();
        input("1 20:01, TableDisplay, td1, payBill");
        
        runAndCheck();
    }
    
    @Test(expected = AssertionError.class)
    public void startOrderTwice() throws AssertionError{
        /* Checks if an assertion error is thrown when there is an attempt to
         * start an order while another order has not yet finished
         * 
         */
        logger.info(makeBanner("startOrderTwice"));
        populateMenu();
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1"); 
        input("1 20:05, TableDisplay, td1, startOrder");
        input("1 20:06, TableDisplay, td1, addMenuItem, D1"); 
        
        runAndCheck();
    }
    
    @Test
    public void addTicketPayBill(){
        /* General success scenario check of the SubmitOrder and PayBill use case
         * Checks if the total is calculated correctly from the given quantities of the items
         * 
         */
        logger.info(makeBanner("addTicketPayBill"));
        input("1 12:00, OfficeKVM, okvm, addToMenu, M23, Cod Fillet, 8.95");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M7, Lasagne, 10.20");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Beer, 4.30");
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, M7");
        input("1 20:01, TableDisplay, td1, addMenuItem, M23");
        input("1 20:01, TableDisplay, td1, addMenuItem, D2");
        input("1 20:01, TableDisplay, td1, addMenuItem, D2");
        input("1 20:02, TableDisplay, td1, submitOrder");
        
        input("1 21:30, TableDisplay, td1, payBill");
        expect("1 21:30, TableDisplay, td1, approveBill, Total:, 27.75");
        
        input("1 21:32, CardReader, cr1, acceptCardDetails, S13TE37");
        expect("1 21:32, BankClient, bc, makePayment, S13TE37, 27.75");
        
        input("1 21:33, BankClient, bc, acceptAuthorisationCode, SKAT");
        expect("1 21:33, ReceiptPrinter, rp1, takeReceipt, Total:, 27.75, AuthCode:, SKAT");
        
        runAndCheck();
    }
    
    //start new order after paying
    @Test
    public void startSecondOrder(){
        /* Check if the user is allowed to start another order after the 
         * previous one is finished (paid)
         * 
         */
        logger.info(makeBanner("startSecondOrder"));
        populateMenu();
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M2");
        input("1 20:01, TableDisplay, td1, addMenuItem, D2");
        input("1 20:01, TableDisplay, td1, addMenuItem, D2");
        input("1 20:02, TableDisplay, td1, submitOrder");
        
        input("1 21:30, TableDisplay, td1, payBill");
        expect("1 21:30, TableDisplay, td1, approveBill, Total:, 26.85");
        
        input("1 21:32, CardReader, cr1, acceptCardDetails, MIHPWNED");
        expect("1 21:32, BankClient, bc, makePayment, MIHPWNED, 26.85");
        
        input("1 21:33, BankClient, bc, acceptAuthorisationCode, ATED");
        expect("1 21:33, ReceiptPrinter, rp1, takeReceipt, Total:, 26.85, AuthCode:, ATED");
        //Previous order has finished (been paid)
        
        input("1 20:35, TableDisplay, td1, startOrder"); //New order (same table)0
        input("1 20:36, TableDisplay, td1, addMenuItem, M1");
        input("1 20:38, TableDisplay, td1, showTicket");
        expect("1 20:38, TableDisplay, td1, viewTicket, tuples, 3, "
                + "ID, Description, Count, "
                + "M1, Cheese burger, 1");
        
        runAndCheck();
    }
    
    @Test
    public void showRack(){
        /* General success scenario check of the SubmitOrder and ShowRack use case
         * Checks if the orders are submitted correctly in the order rack
         * Checks if the items are ordered by their time, 
         * then by the ticket number and finally by the menu item id
         * 
         */
        logger.info(makeBanner("showRack"));
        populateMenu();
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M2");
        input("1 20:02, TableDisplay, td1, addMenuItem, D1");
        input("1 20:02, TableDisplay, td1, addMenuItem, D1");
        input("1 20:03, TableDisplay, td1, addMenuItem, M1");
        input("1 20:04, TableDisplay, td1, submitOrder"); //Order submitted first => Ticket#:1
        input("1 20:15, TableDisplay, td2, startOrder");
        input("1 20:16, TableDisplay, td2, addMenuItem, D2");
        input("1 20:16, TableDisplay, td2, addMenuItem, D2");
        input("1 20:17, TableDisplay, td2, addMenuItem, M2");
        input("1 20:17, TableDisplay, td2, addMenuItem, M2");
        input("1 20:18, TableDisplay, td2, submitOrder"); //Order submitted second => Ticket#:2
        input("1 20:20, Clock, c, tick");
        expect("1 20:20, KitchenDisplay, kd, viewRack, tuples, 6, "
                + "Time, Ticket#, MenuID, Description, #Ordered, #Ready, "
                + "16, 1, D1, Bottled Water, 3, 0, " 
                + "16, 1, M1, Cheese burger, 2, 0, "
                + "16, 1, M2, Ceasar salad, 1, 0, "
                + "2, 2, D2, Beer, 2, 0, "
                + "2, 2, M2, Ceasar salad, 2, 0");
        
        runAndCheck();
    }
    
    public void populateRack(){
        //Populates the rack with some default order tickets
        populateMenu();
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D2");
        input("1 20:02, TableDisplay, td1, addMenuItem, M2");
        input("1 20:02, TableDisplay, td1, addMenuItem, M1");
        input("1 20:02, TableDisplay, td1, addMenuItem, D1");
        input("1 20:03, TableDisplay, td1, addMenuItem, M1");
        input("1 20:04, TableDisplay, td1, submitOrder");
        input("1 20:10, TableDisplay, td2, startOrder");
        input("1 20:11, TableDisplay, td2, addMenuItem, D2");
        input("1 20:11, TableDisplay, td2, addMenuItem, D2");
        input("1 20:12, TableDisplay, td2, addMenuItem, M2");
        input("1 20:13, TableDisplay, td2, addMenuItem, M2");
        input("1 20:14, TableDisplay, td2, submitOrder");
    }
    
    //Maybe add a general indicateItemReady() test
    
    @Test
    public void indicateFirstItemReady(){
        /* Checks if a takeTicket output event is made when the first item 
         * of an order is indicated as finished
         * Also check if the quantity ready is incremented correctly
         */
        logger.info(makeBanner("indicateFirstItemReady"));
        populateRack();
        input("1 20:30, KitchenDisplay, kd, itemReady, 1, D1");
        expect("1 20:30, TicketPrinter, tp, takeTicket, tuples, 3, "
                + "Table:, Tab-1, _, "
                + "ID, Description, Count, "
                + "D1, Bottled Water, 2, "
                + "D2, Beer, 1, "
                + "M1, Cheese burger, 2, "
                + "M2,  Ceasar salad, 1");
        
        
        input("1 20:30, KitchenDisplay, kd, itemReady, 2, D2");
        expect("1 20:30, TicketPrinter, tp, takeTicket, tuples, 3, "
                + "Table:, Tab-2, _, "
                + "ID, Description, Count, "
                + "D2, Beer, 2, "
                + "M2,  Ceasar salad, 2");
        
        input("1 20:31, Clock, c, tick");
        expect("1 20:31, KitchenDisplay, kd, viewRack, tuples, 6, "
                + "Time, Ticket#, MenuID, Description, #Ordered, #Ready,"
                + "27, 1, D1, Bottled Water, 2, 1, "
                + "27, 1, D2, Beer, 1, 0, "
                + "27, 1, M1, Cheese burger, 2, 0, "
                + "27, 1, M2, Ceasar salad, 1, 0, "
                + "17, 2, D2, Beer, 2, 1, "
                + "17, 2, M2, Ceasar salad, 2, 0");
        
        runAndCheck();
    }
    
    @Test
    public void indicateOrderReady(){
        /* Checks that when a whole order is indicated as ready (thus is finished),
         * is removed from the rack and the pass light is switched on
         * 
         */
        logger.info(makeBanner("indicateOrderReady"));
        populateRack();
        input("1 20:30, KitchenDisplay, kd, itemReady, 1, D1");
        expect("1 20:30, TicketPrinter, tp, takeTicket, tuples, 3, "
                + "Table:, Tab-1, _, "
                + "ID, Description, Count, "
                + "D1, Bottled Water, 2, "
                + "D2, Beer, 1, "
                + "M1, Cheese burger, 2, "
                + "M2,  Ceasar salad, 1");
        
        input("1 20:30, KitchenDisplay, kd, itemReady, 1, D1");
        input("1 20:35, KitchenDisplay, kd, itemReady, 1, D2");
        input("1 20:36, KitchenDisplay, kd, itemReady, 1, M1");
        input("1 20:37, KitchenDisplay, kd, itemReady, 1, M1");
        //input("1 20:40, KitchenDisplay, kd, itemReady, 1, D1");
        input("1 20:40, KitchenDisplay, kd, itemReady, 1, M2"); //All items of order 1 finished
        expect("1 20:40, PassLight, pl, viewSwitchedOn");
        
        input("1 20:41, Clock, c, tick");
        expect("1 20:41, KitchenDisplay, kd, viewRack, tuples, 6, "
                + "Time, Ticket#, MenuID, Description, #Ordered, #Ready,"
                + "27, 2, D2, Beer, 2, 0, " //First order is removed from the rack
                + "27, 2, M2, Ceasar salad, 2, 0");
        
        runAndCheck();
    }
    
    @Test(expected = AssertionError.class)
    public void moreItemsReady() throws AssertionError{
        /* Checks if an AssertionError is throw if an item is indicated 
         * ready when the quantity ordered has been reached
         * 
         */
        logger.info(makeBanner("moreItemsReady"));
        populateRack();
        input("1 20:30, KitchenDisplay, kd, itemReady, 1, D1");
        expect("1 20:30, TicketPrinter, tp, takeTicket, tuples, 3, "
                + "Table:, Tab-1, _, "
                + "ID, Description, Count, "
                + "D1, Bottled Water, 2, "
                + "D2, Beer, 1, "
                + "M1, Cheese burger, 2, "
                + "M2,  Ceasar salad, 1");
        
        input("1 20:30, KitchenDisplay, kd, itemReady, 1, D1");
        //Note the order has quantity only 2 of items with id D1
        input("1 20:30, KitchenDisplay, kd, itemReady, 1, D1");
        runAndCheck();
    }
    
    @Test(expected = AssertionError.class)
    public void indicateUnknownItemReady() throws AssertionError{
        /* Checks if an AssertionError is throw if an item is indicated 
         * ready and its not included in the order ticket indicated  
         * 
         */
        logger.info(makeBanner("indicateUnknownItemReady"));
        populateRack();
        input("1 20:30, KitchenDisplay, kd, itemReady, 2, D1"); //menu item with Id D1 is not in the order with id 2
        
        runAndCheck();
    }
    
    @Test(expected = AssertionError.class)
    public void indicateUnknownTicket() throws AssertionError{
        /* Checks if an AssertionError is throw if an item is indicated 
         * ready and its order ticket is not included in the rack
         * 
         */
        logger.info(makeBanner("indicateUnknownTicket"));
        populateRack();
        input("1 20:30, KitchenDisplay, kd, itemReady, 15, D1"); //order with id 15 is not in the rack
        
        runAndCheck();
    }
    
    @Test
    public void generalTest(){
        /* A general check that goes through all the use case, 
         * as it's going to be used in the restaurant
         */
        logger.info(makeBanner("generalTest"));
        //First the menu has to be populated with some items
        input("1 18:00, OfficeKVM, okvm, addToMenu, D1, Bottled Water, 1.00");
        input("1 18:01, OfficeKVM, okvm, addToMenu, D2, Beer, 4.50");
        input("1 18:02, OfficeKVM, okvm, addToMenu, D3, Ouzo, 3.00");
        input("1 18:03, OfficeKVM, okvm, addToMenu, D4, Wine, 5.00");
        input("1 18:04, OfficeKVM, okvm, addToMenu, D5, Soda, 2.50");
        input("1 18:05, OfficeKVM, okvm, addToMenu, M1, Cheese burger, 9.95");
        input("1 18:06, OfficeKVM, okvm, addToMenu, M2, Ceasar salad, 7.90");
        input("1 18:06, OfficeKVM, okvm, addToMenu, M3, Hunter's Chicken, 7.50");
        input("1 18:07, OfficeKVM, okvm, addToMenu, M4, Haggis neeps and tatties, 8.50");
        input("1 18:07, OfficeKVM, okvm, addToMenu, M5, Greek Gyros, 8.00");
        //Update wine price
        input("1 18:08, OfficeKVM, okvm, addToMenu, D4, Wine, 4.00"); 
        
        //Remove Greek Gyros from the menu
        input("1 18:10, OfficeKVM, okvm, removeFromMenu, M5");
        
        input("1 18:10, OfficeKVM, okvm, showMenu");
        expect("1 18:10, OfficeKVM, okvm, viewMenu, tuples, 3, "
                + "    ID,              Description, Price, "
                + "    D1,            Bottled Water,  1.00, "
                + "    D2,                     Beer,  4.50, "
                + "    D3,                     Ouzo,  3.00, "
                + "    D4,                     Wine,  4.00, "
                + "    D5,                     Soda,  2.50, "
                + "    M1,            Cheese burger,  9.95, "
                + "    M2,             Ceasar salad,  7.90, "
                + "    M3,         Hunter's Chicken,  7.50, "
                + "    M4, Haggis neeps and tatties,  8.50");
        
        //Table 1 starts order
        input("1 20:00, TableDisplay, td1, startOrder"); 
        input("1 20:01, TableDisplay, td1, addMenuItem, M4");
        input("1 20:01, TableDisplay, td1, addMenuItem, M4");
        input("1 20:02, TableDisplay, td1, addMenuItem, D1");
        input("1 20:02, TableDisplay, td1, addMenuItem, D2");
        //Table 2 starts order
        input("1 20:03, TableDisplay, td2, startOrder"); 
        input("1 20:03, TableDisplay, td2, addMenuItem, M2");
        
        input("1 20:03, TableDisplay, td1, addMenuItem, M1");
        
        input("1 20:04, TableDisplay, td2, addMenuItem, M1");
        //Table 2 wants to preview its order ticket before submitting its order
        input("1 20:04, TableDisplay, td2, showTicket");
        expect("1 20:04, TableDisplay, td2, viewTicket, tuples, 3, "
                + "ID, Description, Count, "
                + "M1, Cheese burger, 1, "
                + "M2, Ceasar salad, 1");
        //Table 2 submitted its order
        input("1 20:05, TableDisplay, td2, submitOrder"); 
        //Clock tick
        input("1 20:07, Clock, c, tick");
        expect("1 20:07, KitchenDisplay, kd, viewRack, tuples, 6, "
                + "Time, Ticket#, MenuID, Description, #Ordered, #Ready, "
                + "2, 1, M1, Cheese burger, 1, 0, "
                + "2, 1, M2, Ceasar salad, 1, 0");
        //Table 1 previews its order ticket
        input("1 20:08, TableDisplay, td1, showTicket");
        expect("1 20:08, TableDisplay, td1, viewTicket, tuples, 3, "
                + "ID, Description, Count, "
                + "D1, Bottled Water, 1, "
                + "D2, Beer, 1, "
                + "M1, Cheese burger, 1, "
                + "M4, Haggis neeps and tatties, 2");
        //Table 1 removes M1 from order
        input("1 20:09, TableDisplay, td1, removeMenuItem, M1");
        //Table 1 submits order
        input("1 20:09, TableDisplay, td1, submitOrder");
        //Clock tick
        input("1 20:10, Clock, c, tick");
        expect("1 20:10, KitchenDisplay, kd, viewRack, tuples, 6, "
                + "Time, Ticket#, MenuID, Description, #Ordered, #Ready, "
                + "5,       1,     M1,            Cheese burger,        1,      0, "
                + "5,       1,     M2,             Ceasar salad,        1,      0, "
                + "1,       2,     D1,            Bottled Water,        1,      0, "
                + "1,       2,     D2,                     Beer,        1,      0, "
                + "1,       2,     M4, Haggis neeps and tatties,        2,      0");
        //Chef indicates that the first item of the first order is ready
        input("1 20:12, KitchenDisplay, kd, itemReady, 1, M2");
        expect("1 20:12, TicketPrinter, tp, takeTicket, tuples, 3, "
                + "Table:, Tab-2, _, "
                + "ID, Description, Count, "
                + "M1, Cheese burger,     1, "
                + "M2,  Ceasar salad,     1");
        //Chef indicates that the first item of the second order is ready
        input("1 20:14, KitchenDisplay, kd, itemReady, 2, D1");
        expect("1 20:14, TicketPrinter, tp, takeTicket, tuples, 3, "
                + "Table:, Tab-1, _, "
                + "ID, Description, Count, "
                + "D1,            Bottled Water,     1, "
                + "D2,                     Beer,     1, "
                + "M4, Haggis neeps and tatties,     2");
        //Other items ready
        input("1 20:19, KitchenDisplay, kd, itemReady, 2, D2");
        //First order (table 2) is ready
        input("1 20:19, KitchenDisplay, kd, itemReady, 1, M1");
        expect("1 20:19, PassLight, pl, viewSwitchedOn");
        //Other items ready
        input("1 20:21, KitchenDisplay, kd, itemReady, 2, M4");
        //Clock Tick
        input("1 20:22, Clock, c, tick");
        expect("1 20:22, KitchenDisplay, kd, viewRack, tuples, 6, "
                + "Time, Ticket#, MenuID, Description, #Ordered, #Ready, "
                + "13,       2,     D1,            Bottled Water,        1,      1, "
                + "13,       2,     D2,                     Beer,        1,      1, "
                + "13,       2,     M4, Haggis neeps and tatties,        2,      1"); 
        //No orders ready so waiter closes pass light
        input("1 20:23, PassButton, pb, press");
        expect("1 20:23, PassLight, pl, viewSwitchedOff");
        //Table 2 pays its bill
        input("1 21:25, TableDisplay, td2, payBill");
        expect("1 21:25, TableDisplay, td2, approveBill, Total:, 17.85"); 
        
        input("1 21:26, CardReader, cr2, acceptCardDetails, MIHPZNED");
        expect("1 21:26, BankClient, bc, makePayment, MIHPZNED, 17.85");
        
        input("1 21:27, BankClient, bc, acceptAuthorisationCode, ATED");
        expect("1 21:27, ReceiptPrinter, rp2, takeReceipt, Total:, 17.85, AuthCode:, ATED");
        //New Customer Table 2 starts order
        input("1 20:28, TableDisplay, td2, startOrder");
        input("1 20:28, TableDisplay, td2, addMenuItem, M1");
        input("1 20:29, TableDisplay, td2, addMenuItem, M2");
        input("1 20:29, TableDisplay, td2, addMenuItem, D2");
        input("1 20:30, TableDisplay, td2, addMenuItem, D2");
        //Table 2 submits order
        input("1 20:30, TableDisplay, td2, submitOrder");
        //Clock tick
        input("1 20:31, Clock, c, tick");
        expect("1 20:31, KitchenDisplay, kd, viewRack, tuples, 6, "
                + "Time, Ticket#, MenuID, Description, #Ordered, #Ready, "
                + "22,       2,     D1,            Bottled Water,        1,      1, "
                + "22,       2,     D2,                     Beer,        1,      1, "
                + "22,       2,     M4, Haggis neeps and tatties,        2,      1, "
                + " 1,       3,     D2,                     Beer,        2,      0, "
                + " 1,       3,     M1,            Cheese burger,        1,      0, "
                + " 1,       3,     M2,             Ceasar salad,        1,      0");
        //Second order (table 1) is ready
        input("1 20:32, KitchenDisplay, kd, itemReady, 2, M4");
        expect("1 20:32, PassLight, pl, viewSwitchedOn");
        //No orders ready so waiter closes pass light
        input("1 20:23, PassButton, pb, press");
        expect("1 20:23, PassLight, pl, viewSwitchedOff");
        //Chef indicates that the first item of the third order is ready
        input("1 20:25, KitchenDisplay, kd, itemReady, 3, D2");
        expect("1 20:25, TicketPrinter, tp, takeTicket, tuples, 3, "
                + "Table:, Tab-2, _, "
                + "ID,   Description, Count, "
                + "D2,          Beer,     2, "
                + "M1, Cheese burger,     1, "
                + "M2,  Ceasar salad,     1");//TODO: CHANGE, NOTICE IF ITS TAB-2
        input("1 20:26, KitchenDisplay, kd, itemReady, 3, D2");
        input("1 20:35, KitchenDisplay, kd, itemReady, 3, M1");
        //Third order (table 3) is ready
        input("1 20:37, KitchenDisplay, kd, itemReady, 3, M2");
        expect("1 20:37, PassLight, pl, viewSwitchedOn");
        //Table 1 pays its bill
        input("1 21:40, TableDisplay, td1, payBill");
        expect("1 21:40, TableDisplay, td1, approveBill, Total:, 22.50"); 
        
        input("1 21:41, CardReader, cr1, acceptCardDetails, ERTKODS");
        expect("1 21:41, BankClient, bc, makePayment, ERTKODS, 22.50"); 
        
        input("1 21:42, BankClient, bc, acceptAuthorisationCode, SDED");
        expect("1 21:42, ReceiptPrinter, rp1, takeReceipt, Total:, 22.50, AuthCode:, SDED");
        //No orders ready so waiter closes pass light
        input("1 20:43, PassButton, pb, press");
        expect("1 20:43, PassLight, pl, viewSwitchedOff");
        //Table 2 pays its bill
        input("1 21:55, TableDisplay, td2, payBill");
        expect("1 21:55, TableDisplay, td2, approveBill, Total:, 26.85");
        
        input("1 21:56, CardReader, cr2, acceptCardDetails, DEAFSFES");
        expect("1 21:56, BankClient, bc, makePayment, DEAFSFES, 26.85");
        
        input("1 21:57, BankClient, bc, acceptAuthorisationCode, ATDD");
        expect("1 21:57, ReceiptPrinter, rp2, takeReceipt, Total:, 26.85, AuthCode:, ATDD");

        runAndCheck();
    }
    
    
   /*
    * Put all your JUnit system-level tests above.
    ***********************************************************************
    * END MODIFICATION AREA
    ***********************************************************************
    */

    
    
   /**
    * Set up system to be tested and connect interface devices to 
    * event distributor and collector. 
    * 
    * Setup is divided into two parts:
    * 
    * 1. Where IO device objects are created and connected to event
    *    distributor and event collector.
    *    
    * 2. Where core system implementation objects are created and 
    *    links are added between all the implementation objects and
    *    the IO device objects.
    *    
    * The first part configures sufficient IO device objects for all 3
    * implementation phases defined in the Coursework 3 handout.  It should 
    * not be touched. 
    * 
    * The second part herTickete only gives the configuration for the demonstration
    * design.  It has to be modified for the system implementation.
    *   
    * 
    */
    @Override
    protected void setupSystem() {

        /* 
         * PART 1: 
         * - IO Device creation.  
         * - Establishing links between IO device objects and test framework objects.
         */
        
        
        /*
         * IO DEVICES FOR KITCHEN AREA
         * 
         * Create IO objects for kitchen.
         * Connect them to the event distributor and event collector.
         */
        
        KitchenDisplay display = new KitchenDisplay("kd");
        display.addDistributorLinks(distributor);
        display.setCollector(collector);
        
        Clock.initialiseInstance();
        Clock.getInstance().addDistributorLinks(distributor);
                
        TicketPrinter printer = new TicketPrinter("tp");
        printer.setCollector(collector);
      
        /* 
         * IO DEVICES FOR PASS
         */

        /*
         * Create IO objects for PASS
         * Connect them to the event distributor and event collector.
         */

        PassLight light = new PassLight("pl");
        light.setCollector(collector);
        
        PassButton button = new PassButton("pb");
        button.addDistributorLinks(distributor);
        
        
        // IO DEVICES FOR OFFICE
        
        // Create IO objects for office.
        // Connect them to the event distributor and event collector.
        
        OfficeKVM officeKVM = new OfficeKVM("okvm");
        officeKVM.addDistributorLinks(distributor);
        officeKVM.setCollector(collector);
        
        BankClient bankClient = new BankClient("bc");
        bankClient.addDistributorLinks(distributor);
        bankClient.setCollector(collector);
        
                
        // IO DEVICE FOR TABLES
        
        final int NUM_TABLES = 2;
        
        List<TableDisplay> tableDisplays = new ArrayList<TableDisplay>();
        List<ReceiptPrinter> receiptPrinters = new ArrayList<ReceiptPrinter>();
        List<CardReader> cardReaders = new ArrayList<CardReader>();
        
        for (int i = 1; i <= NUM_TABLES; i++) {
            
            String iString = Integer.toString(i);
            
            // Create IO objects for a table.
            // Connect to event distributor and collector
            
            TableDisplay tableDisplay = 
                    new TableDisplay("td" + iString);
            tableDisplay.addDistributorLinks(distributor);
            tableDisplay.setCollector(collector);
            tableDisplays.add(tableDisplay);
            
            //setting up all receipt printers
            ReceiptPrinter receiptPrinter = 
                    new ReceiptPrinter("rp" + iString);
            receiptPrinter.setCollector(collector);
            receiptPrinters.add(receiptPrinter);
            
            //setting up all card readers
            CardReader cardReader = 
                    new CardReader("cr" + iString);
            cardReader.addDistributorLinks(distributor);
            cardReaders.add(cardReader);
            
           
        }
        
        /* 
         * PART 2: 
         * - Implementation object creation. 
         * - Establishing links between different implementation 
         *   objects and IO Device objects and implementation objects.
         */

        /*
         ******************************************************************
         * BEGIN MODIFICATION AREA
         ******************************************************************
         * Put below code for creating implementation objects
         * and connecting them to the interface device objects.
         */

        Rack rack = new Rack();

        // Create KitchenCoordinator object and links between it and IO devices.
        
        KitchenCoordinator kitchenCoordinator = new KitchenCoordinator();
        Clock.getInstance().setKitchenCoordinator(kitchenCoordinator);
        button.setKitchenCoordinator(kitchenCoordinator);
        display.setKitchenCoordinator(kitchenCoordinator);
        kitchenCoordinator.setPassLight(light);
        kitchenCoordinator.setKitchenDisplay(display);
        kitchenCoordinator.setOrderRack(rack);
        kitchenCoordinator.setTicketPrinter(printer);
        
        
        OfficeOperations officeOperations = new OfficeOperations();
        officeKVM.setOfficeOperations(officeOperations);
                
        
        // TABLE-RELATED
        for (int i = 1; i <= NUM_TABLES; i++) {
            
            String iString = Integer.toString(i);

            // This tableID is for later printing on tickets at the pass, in 
            // order to show which table is to receive the order.
            // Go look at the ReceiptPrinter class.
            
            // DO NOT CHANGE this scheme for naming table IDs.
            
            String tableID = "Tab-" + iString;

            // Create table-related core objects.
            // Connect these objects to table-related IO objects and to other system 
            // components.
            
            //Update the connections of the table Display to the table related objects
            int index = i-1;
            tableDisplays.get(index).setOfficeOperations(officeOperations);
            tableDisplays.get(index).setKitchenCoordinator(kitchenCoordinator);
            tableDisplays.get(index).setId(tableID);
            
            tableDisplays.get(index).getCashier().setCardReader(cardReaders.get(index));
            tableDisplays.get(index).getCashier().setReceiptPrinter(receiptPrinters.get(index));
            tableDisplays.get(index).getCashier().setBankClient(bankClient);
            
         }

        // GENERAL
        // Create implementation objects that link to more than one area
        // and add links between implementation objects and interface device
        // objects in different areas.
        
        
        /*
         * Put above code for creating implementation objects
         * and connecting them to the interface device objects.
         ******************************************************************
         * END MODIFICATION AREA
         ******************************************************************
         */

        
    }
    
    
   
    
}
