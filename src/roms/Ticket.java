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
    //indicates if the first menu item in the order has already been prepared
    private boolean firstItemReady=false;
    //indicates if all the orderItems of the Ticket are ready   
    private boolean finished=false;
    
    public Ticket(String tableID) {
        logger.fine("Entry");
        //amount is initialised to 0
        amount=new Money();
        this.tableID=tableID;
    }
    
    public boolean isFinished() {
        return finished;
    }
    
    /*
     * called each time an item in the ticket is indicated ready
     * checks all orderItems in the list if they are done
     * if all of the are, it sets the finished boolean field to true
     */
    public void checkFinished() {
        for (OrderItem orderItem: order){
            if (!orderItem.isDone()){
                finished=false;
                return;
            }
        }
        finished=true;
    }

    public boolean isFirstItemReady() {
        return firstItemReady;
    }

    public void setFirstItemReady(boolean firstItemReady) {
        this.firstItemReady = firstItemReady;
    }
    
    public void setId(int id){
        //Id is set when the ticket is submitted
        this.id=id;
    }
    
    public int getId(){
        return id;
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
    
    public void setDate(Date date) {
        this.date=date;
    }
    
    public Date getDate() {
        return date;
    }
    
    public ArrayList<OrderItem> getOrder(){
        return order;
    }
    
    //retrieves the orderItem with the given menuID from the order list
    public OrderItem getOrderItem(String menuID){
        OrderItem target=null;
        for (OrderItem item:order){
            if (item.getItem().getId().equals(menuID)){
                target=item;
                break;
            }
        }
        assert (target!=null): "Attempt to retrieve a non-existing OrderItem from a ticket";
        return target;
    }
    
    //adds a new menuItem in the order list
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
    
    //removes the menu item with menuItemID from the order list
    public void remove(String menuItemId){
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
            OrderItem item= order.get(i);
            stringArray[i]=item.getItem().getId();
            stringArray[i+1]=item.getItem().getDescription();
            stringArray[i+2]=Integer.toString(item.getQuantity());
        }
        List<String> ss = new ArrayList<String>();
        ss.addAll(Arrays.asList(stringArray));
        return ss;
    }
    
    
}
