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
    
    public Ticket(String tableID) {
        logger.fine("Entry");
        //amount is initialised to 0
        amount=new Money();
        this.tableID=tableID;
        date=Clock.getInstance().getDateAndTime();
    }

    public Money getAmount() {
        return amount;
    }
    
    public String getTableID() { 
        return tableID; 
    }

    //setAmount is only used internally to update the total amount so it's private
    private void setAmount(Money amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }
    
    public void add(MenuItem item){
        boolean exists=false;
        for (OrderItem orderItem: order){
            if (orderItem.getItem().equals(orderItem)){
                //item already exists in the order list, just increment the quantity
                exists=true;
                orderItem.incrementQuantity(1);
                break;
            }
        }
        if (!exists){
            //if it doesn't exist, we need to put a new OrderItem in the order list
            order.add(new OrderItem(item));
        }
        //update the total amount
        setAmount(getAmount().add(item.getPrice()));
    }
    
    public void remove(String menuItemId){
        boolean exists=false;
        for (OrderItem orderItem: order){
            if (orderItem.getItem().getMenuItemId().equals(menuItemId)){
                //item exists in the order list
                exists=true;
                //update the total amount
                setAmount(getAmount().add(new Money("-"+orderItem.getItem().getPrice().toString())));
                //if the target OrderItem's quantity is just 1, remove the item from the list
                if (orderItem.getQuantity()==1){
                    order.remove(orderItem);
                }else{
                    //otherwise just decrement the quantity
                    orderItem.incrementQuantity(-1);
                }
                break;
            }
        }
        //if the target item doesn't exist throw an AssertionError
        assert (!exists):"Attempt to delete non-existing item from order ticket";
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
        for (int i=0;i<stringArray.length;i+=3){
            MenuItem item= order.get(i).getItem();
            stringArray[i]=item.getMenuItemId();
            stringArray[i+1]=item.getDescription();
            stringArray[i+2]=item.getPrice().toString();
        }
        List<String> ss = new ArrayList<String>();
        ss.addAll(Arrays.asList(stringArray));
        return ss;
    }
    
    
}
