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
        input("1 12:00, OfficeKVM, okvm, addToMenu, M1, Cheese burger, 9.95");
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
         * attempt to submit an order when an order has not
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
    public void submitOrder(){
        /* General success scenario check of the submitOrder use case
         * Checks if the items are ordered by their Id
         * Checks if items in order tickets are displayed correctly, 
         * and ordered by ticket number and then by menu item id
         * 
         */
        logger.info(makeBanner("submitOrder"));
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M2");
        input("1 20:02, TableDisplay, td1, addMenuItem, D1");
        input("1 20:02, TableDisplay, td1, addMenuItem, D1");
        input("1 20:03, TableDisplay, td1, addMenuItem, M1");
        input("1 20:04, TableDisplay, td1, submitOrder");
        input("1 20:15, TableDisplay, td2, startOrder");
        input("1 20:16, TableDisplay, td2, addMenuItem, D2");
        input("1 20:16, TableDisplay, td2, addMenuItem, D2");
        input("1 20:17, TableDisplay, td2, addMenuItem, M2");
        input("1 20:17, TableDisplay, td2, addMenuItem, M2");
        input("1 20:18, TableDisplay, td2, submitOrder");
        input("1 20:20, KitchenDisplay, kd, displayRack, rack");
        
        runAndCheck();
    }
    
    @Test
    public void addTicketPayBill(){
        /* General success scenario check of the addTicketPayBill use case
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
        
        input("1 21:32, CardReader, cr1, acceptCardDetails, STE1337");
        expect("1 21:32, BankClient, bc, makePayment, STE1337, 27.75");
        
        input("1 21:33, BankClient, bc, acceptAuthorisationCode, SKAT");
        expect("1 21:33, ReceiptPrinter, rp1, takeReceipt, Total:, 27.75, AuthCode:, SKAT");
        
        runAndCheck();
    }
    
    //start new order after paying
    
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
            
            
            ReceiptPrinter receiptPrinter = 
                    new ReceiptPrinter("rp" + iString);
            receiptPrinter.setCollector(collector);
            receiptPrinters.add(receiptPrinter);
            
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

        // SYSTEM CORE
        // Create systemCore object and links between it and IO devices.
        
        KitchenCoordinator kitchenCoordinator = new KitchenCoordinator();
       
        button.setKitchenCoordinator(kitchenCoordinator);
        kitchenCoordinator.setPassLight(light);
        
        OfficeOperations officeOperations = new OfficeOperations();
        officeKVM.setOfficeOperations(officeOperations);
        
        Rack rack = new Rack();

        Cashier cashier = new Cashier();
        
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
            tableDisplays.get(index).setRack(rack);
            tableDisplays.get(index).setId(tableID);
            tableDisplays.get(index).setCashier(cashier);
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
