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
    

    public Ticket(TicketId id) {
        logger.fine("Entry");
        this.id = id;
        amount=new Money();
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
 
        // Dummy implementation. 
        String[] stringArray = 
            {"D1", "Wine",        "1",
             "D2", "Soft drink",  "3",
             "M1", "Fish",        "2",
             "M2", "Veg chili",   "2"
            };
        List<String> ss = new ArrayList<String>();
        ss.addAll(Arrays.asList(stringArray));
        return ss;
    }
    
    
}
