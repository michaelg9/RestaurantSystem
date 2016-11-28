/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author pbj
 *
 */
public class Ticket {
    //list which holds all the ordered MenuItems
    private ArrayList<OrderItem> order=new ArrayList<OrderItem>(); 
    protected static final Logger logger = Logger.getLogger("roms");
    //amount holds the total amount of the order
    private Money amount;
    //date holds the simulated date when the order was started
    private Date date;
    private String tableID;
    private int id;
    
    public Ticket(String tableID) {
        logger.fine("Entry");
        //amount is initialised to 0
        amount=new Money();
        this.tableID=tableID;
    }
    
    public void setId(int id){
        //Id is set when the ticket is submitted
        logger.fine("Entry");
        this.id=id;
    }
    
    public int getId(){
        logger.fine("Entry");
        return id;
    }
    
    public Money getAmount() {
        logger.fine("Entry");
        return amount;
    }
    
    public String getTableID() { 
        logger.fine("Entry");
        return tableID; 
    }

    //setAmount is only used internally to update the total amount so it's private
    private void setAmount(Money amount) {
        logger.fine("Entry");
        this.amount = amount;
    }
    
    public void setDate(Date date) {
        logger.fine("Entry");
        this.date=date;
    }
    
    public Date getDate() {
        logger.fine("Entry");
        return date;
    }
    
    public ArrayList<OrderItem> getOrder(){
        logger.fine("Entry");
        return order;
    }
    
    public void add(MenuItem item){
        logger.fine("Entry");
        boolean exists=false;
        for (OrderItem orderItem: order){
            if (orderItem.getItem().equals(item)){
                //item already exists in the order list, just increment the quantity
                exists=true;
                orderItem.incrementQuantity(1);
                logger.fine("Item already in Order Ticket, quantity incremented successfully");
                break;
            }
        }
        if (!exists){
            /* if it doesn't exist, we need to put a new OrderItem in the order list 
             * in the correctly ordered position according to the menu item id
             */
            boolean added=false;
            for(int i=0;i<order.size();i++){
                String currentItemId=order.get(i).getItem().getId();
                String addItemId = item.getId();
                int comparison = currentItemId.compareTo(addItemId);
                //if item already exists, exit with appropriate error message
                if(comparison > 0){
                    order.add(i,new OrderItem(item));
                    added=true;
                    logger.fine("Item added to the Order Ticket successfully");
                    break;
                }
            }
            if(!added){
                order.add(new OrderItem(item));
                logger.fine("Item added to the Order Ticket successfully");
            }
        }
        //update the total amount
        setAmount(getAmount().add(item.getPrice()));
    }
    
    public void remove(String menuItemId){
        logger.fine("Entry");
        boolean exists=false;
        for (OrderItem orderItem: order){
            if (orderItem.getItem().getId().equals(menuItemId)){
                //item exists in the order list
                exists=true;
                //update the total amount
                setAmount(getAmount().add(new Money("-"+orderItem.getItem().getPrice().toString())));
                //if the target OrderItem's quantity is just 1, remove the item from the list
                if (orderItem.getQuantity()==1){
                    order.remove(orderItem);
                    logger.fine("Item removed from the Order Ticket successfully");
                }else{
                    //otherwise just decrement the quantity
                    orderItem.incrementQuantity(-1);
                    logger.fine("Item quantity in the Order Ticket decremented successfully");
                }
                break;
            }
        }
        //if the target item doesn't exist throw an AssertionError
        assert (exists):"Attempt to delete non-existing item from order ticket";
    }
    
    /**
     * Format ticket as list of strings, with, per ticket item, 3 strings for 
     * respectively:
     * - MenuID
     * - Description
     * - Count
     * 
     * Items are expected to be ordered by MenuID.
     * 
     * An example list is:
     * 
     * "D1", "Wine",        "1",
     * "D2", "Soft drink",  "3",
     * "M1", "Fish",        "2",
     * "M2", "Veg chili",   "2"
     * 
     * These lists of strings are used by TableDisplay and TicketPrinter
     * to produce formatted ticket output messages.
     * 
     * @return
     */
    public List<String> toStrings() {
        //converts the Ticket to a string for printing
        logger.fine("Entry");
        String[] stringArray=new String[order.size()*3];
        for (int i=0;i<order.size();i++){
            OrderItem item= order.get(i);
            stringArray[i*3]=item.getItem().getId();
            stringArray[i*3+1]=item.getItem().getDescription();
            stringArray[i*3+2]=Integer.toString(item.getQuantity());
        }
        List<String> ss = new ArrayList<String>();
        ss.addAll(Arrays.asList(stringArray));
        return ss;
    }
    
    
}
