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
    
    private ArrayList<OrderItem> order=new ArrayList<OrderItem>(); 
    protected static final Logger logger = Logger.getLogger("roms");
    private Money amount;
    private TicketId id;
    private Date date;
    private String tableID;
    

    public Ticket(TicketId id, String tableID) {
        logger.fine("Entry");
        this.id = id;
        amount=new Money();
        this.tableID=tableID;
    }

    public Money getAmount() {
        return amount;
    }
    
    public String getTableID() { 
        return tableID; 
    }

    public void updateAmount() {
        Money total=new Money();
        for (OrderItem item : order){total=total.add(item.getItem().getPrice());}
        setAmount(total);
    }

    private void setAmount(Money amount) {
        this.amount = amount;
    }

    public TicketId getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
    
    public void add(OrderItem item){
        assert
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
            stringArray[i]=item.getMenuItemId().getId();
            stringArray[i+1]=item.getDescription();
            stringArray[i+2]=item.getPrice().toString();
        }
        List<String> ss = new ArrayList<String>();
        ss.addAll(Arrays.asList(stringArray));
        return ss;
    }
    
    
}
