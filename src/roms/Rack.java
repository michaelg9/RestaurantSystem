/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The Order rack
 * 
 * @author pbj
 *
 */
public class Rack {
    //Keeps the last Ticket number assigned during the submission of a ticket
    private int counter = 0; 
    //Hold all the order in the Order Rack currently to be processed in the kitchen
    private ArrayList<Ticket> orders=new ArrayList<Ticket>(); 
    protected static final Logger logger = Logger.getLogger("roms");
    
    public int getCounter(){
        //Returns the Ticket number to be assigned during the submission of a ticket
        counter++; //Increment it so that the new ticket submitted takes the a new unique integer as Ticket Id
        return counter;
    }
    
    public void submitOrder(Ticket order){
        //Each order is added at the back of the order rack
        logger.fine("Adding ticket to rack...");
        orders.add(order);
    }
    
    public void removeOrder(int orderId){
        logger.fine("Removing ticket from rack...");
        boolean removed = false;
        for(int i=0; i<orders.size();i++){
            if(orders.get(i).getId() == orderId){
                //target ticket found
                orders.remove(i);
                removed = true;
                logger.fine("Order removed from the order rack successfully");
                break;
            }
        }
        //if it hasn't been removed then it doesn't exist
        assert(removed):"Order to be removed not in order rack";
        return;
    }
    
    public void indicateItemReady(int ticketNumber, String menuID){
        logger.fine("Entry");
        Ticket targetTicket=getTicket(ticketNumber);
        OrderItem targetOrderItem=targetTicket.getOrderItem(menuID);
        targetOrderItem.incrementQuantityReady();
        if (!targetTicket.isFirstItemReady()){
            logger.fine("First item of an order ticket ready");
            targetTicket.setFirstItemReady();
        }
    }
    
    public Ticket getTicket(int ticketNumber){
        Ticket targetTicket=null;
        for (Ticket ticket: orders){
            if (ticket.getId()==ticketNumber){
                targetTicket=ticket;
                break;
            }
        }
        assert (targetTicket!=null) : "Indicate item ready on a non-existing ticket";
        return targetTicket;
    }
    /**
     * Format rack contents as list of strings, with, per order item in each
     * order, 6 strings for respectively:

     * - Time - elapsed time in minutes since order submitted.
     * - Ticket number 
     * - MenuID identifying an order item on the ticket
     * - Description of Menu item with MenuID
     * - Count, the number of MenuID items ordered 
     * - Ready number, the number of MenuID items actually ready
     * 
     * All 6 tuples for order items in a given order have the same time and
     * number.  This format with the time and ticket number repeated is not
     * the most elegant, but is chosen for simplicity. 
     *  
     * The rack presents the contents of each order ticket with one or more
     * incomplete item orders.  Tickets are placed in order of urgency, the most
     * urgent first.  Specifically,  
     * Order items are expected to be ordered 
     *  - First by Ticket number, in increasing order
     *    (Each submitted ticket gets a unique number, and numbers are allocated
     *     in ascending order, so lower numbered tickets are always more urgent
     *     than higher numbered tickets.)
     *  - second, by MenuID.
     * 
     * An example list is:
     * 
     * "15", "1", "D1", "Wine",       "1", "0", 
     * "15", "1", "D3", "Tap water",  "2", "2",
     * "15", "1", "M1", "Fish",       "3", "0",
     * "9",  "2", "D4", "Coffee",     "2", "2",
     * "9",  "2", "P2", "Cake",       "2", "1" 
     * 
     * This list of strings is used by the KitchenDisplay to show the 
     * contents of the rack.
     * 
     * @return
     */
    public List<String> toStrings() {
        logger.fine("Entry");
        List<String> ss = new ArrayList<String>();
        //Items already ordered
        for (Ticket ticket:orders){
            ArrayList<OrderItem> order = ticket.getOrder();
            for (OrderItem item: order){
                ss.add(Integer.toString(Clock.minutesBetween(ticket.getDate(), Clock.getInstance().getDateAndTime())));
                ss.add(Integer.toString(ticket.getId()));
                ss.add(item.getItem().getId());
                ss.add(item.getItem().getDescription());
                ss.add(Integer.toString(item.getQuantity()));
                ss.add(Integer.toString(item.getQuantityReady()));
            }
        }       
        
        return ss;
    }
    
}
