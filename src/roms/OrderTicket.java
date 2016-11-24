package roms;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

public class OrderTicket {
    private ArrayList<OrderItem> order=new ArrayList<OrderItem>(); 
    protected static final Logger logger = Logger.getLogger("roms");
    private Money amount;
    private OrderTicketId id;
    private Date date;
    
    public OrderTicket(OrderTicketId id) {
        logger.fine("Entry");
        this.id = id;
        amount=new Money();
        date=Clock.getStartDate();
    }

    public Money getAmount() {
        return amount;
    }

    public void updateAmount() {
        Money total=new Money();
        for (OrderItem item : order){total=total.add(item.getItem().getPrice());}
        setAmount(total);
    }

    private void setAmount(Money amount) {
        this.amount = amount;
    }

    public OrderTicketId getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
}
