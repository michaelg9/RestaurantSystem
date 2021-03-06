/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Task-level model of a touch-screen display at a table.
 * 
 * @author pbj
 *
 */
public class TableDisplay extends AbstractIODevice {

    
    /**
     * 
     * @param instanceName  
     */
    public TableDisplay(String instanceName) {
        super(instanceName);   
    }
     
    
    /** 
     *    Select device action based on input event message
     *    
     *    @param e
     */
    @Override
    public void receiveEvent(Event e) {
 
         if (e.getMessageName().equals("startOrder") 
                && e.getMessageArgs().size() == 0) {
            
            startOrder();
            
        } else if (e.getMessageName().equals("showMenu") 
                && e.getMessageArgs().size() == 0) {
            
            showMenu();
            
        } else if (e.getMessageName().equals("showTicket") 
                && e.getMessageArgs().size() == 0) {
            
            showTicket();

        } else if (e.getMessageName().equals("addMenuItem") 
                && e.getMessageArgs().size() == 1) {
            
            String menuID = e.getMessageArg(0);
            addMenuItem(menuID);
            
        } else if (e.getMessageName().equals("removeMenuItem") 
                && e.getMessageArgs().size() == 1) {
            
            String menuID = e.getMessageArg(0);
            removeMenuItem(menuID);
            
        } else if (e.getMessageName().equals("submitOrder") 
                && e.getMessageArgs().size() == 0) {
            
            submitOrder();

        } else if (e.getMessageName().equals("payBill") 
                && e.getMessageArgs().size() == 0) {
            
            payBill();

        } else {
            super.receiveEvent(e);
        } 
    }
 
    /*
     ***********************************************************************
     * BEGIN MODIFICATION AREA
     ***********************************************************************
     *  
     * Add private field(s) for associations to classes that TableDisplay 
     * objects need to send messages to. 
     * 
     * Add public setters for these fields.
     * 
     * Complete the methods for handling trigger input events.
     * 
     */

    /*
     * FIELD(S) AND SETTER(S) FOR MESSAGE DESTINATIONS
     */
    //mediator class for Menu Operations
    OfficeOperations officeOps;
    public void setOfficeOperations(OfficeOperations officeOps){
        this.officeOps = officeOps;
    }
    
    //mediator class for the submit order use case
    KitchenCoordinator kitchenCoordinator;
    public void setKitchenCoordinator(KitchenCoordinator kitchenCoordinator){
        this.kitchenCoordinator = kitchenCoordinator;
    }
    
    //mediator class for the pay bill use case
    Cashier cashier = new Cashier();
    public Cashier getCashier(){
        return cashier;
    }
    //represents the ticket currently being created by this  
    //table display or while being processed in the kitchen
    Ticket ticket;

    //id of the each table display is the table's tableID 
    String id;
    public void setId(String id){
        this.id = id;
    }
    

    
    /*
     * SUPPORT FOR TRIGGER INPUT MESSAGES
     */

    public void startOrder() {
        logger.fine(getInstanceName());
        //Check if an order is already in place
        //We assume that no other orders can be made,
        //until the bill of an already placed order is paid
        assert(ticket == null):"Order already in place";
        ticket  = new Ticket(id);
    }
    public void showMenu() {
        logger.fine(getInstanceName());
        assert(ticket != null):"An order has to be initiated first";
        displayMenu(officeOps.getMenu());
    }
    public void showTicket() {
        logger.fine(getInstanceName());
        assert(ticket != null):"An order has to be initiated first";
        displayTicket(ticket);
    }
    public void addMenuItem(String menuID) {
        logger.fine(getInstanceName());
        assert(ticket != null):"An order has to be initiated first";
        ticket.add(officeOps.getMenu().getItem(menuID));
    }
    public void removeMenuItem(String menuID) {
        logger.fine(getInstanceName());
        assert(ticket != null):"An order has to be initiated first";
        ticket.remove(menuID);
    }
    public void submitOrder() {
        logger.fine(getInstanceName());
        assert(ticket != null):"An order has to be initiated first";
        kitchenCoordinator.submitOrder(ticket);
    }
    public void payBill() {
        logger.fine(getInstanceName());
        assert(ticket != null):"An order has to be initiated first";
        //approveBill output event, displays that total amount to the screen for confirmation
        Money total=ticket.getAmount();
        //display total amount to the customer
        displayBill(total);
        //trigger cashier class to handle the pay bill use case
        cashier.pay(total);
        //ticket is nulled so a new customer can start a new order
        ticket = null;
        logger.fine("System ready for another order");        
    }

    /*
     * Put all class modifications above.
     **********************************************************************
     * END MODIFICATION AREA
     *********************************************************************
     */
    
    /*
     *  
     * SUPPORT FOR OUTPUT MESSAGES
     */

    public void displayMenu(Menu menu) {
        logger.fine(getInstanceName());
      
        List<String> messageArgs = new ArrayList<String>();
        String[] preludeArgs = 
            {"tuples","3",
             "ID", "Description", "Price"};
        messageArgs.addAll(Arrays.asList(preludeArgs));
        messageArgs.addAll(menu.toStrings());
        sendMessage("viewMenu", messageArgs);
      
    }
    
    public void displayTicket(Ticket ticket) {
        logger.fine(getInstanceName());
      
        List<String> messageArgs = new ArrayList<String>();
        String[] preludeArgs = 
            {"tuples","3",
             "ID", "Description", "Count"};
        messageArgs.addAll(Arrays.asList(preludeArgs));
        messageArgs.addAll(ticket.toStrings());
        sendMessage("viewTicket", messageArgs);
      
    }
    
    public void displayBill(Money total) {
        logger.fine("Entry");
        List<String> messageArgs = new ArrayList<String>();
        messageArgs.add("Total:");
        messageArgs.add(total.toString());
        sendMessage("approveBill", messageArgs);
               
    }

     
}
