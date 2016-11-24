package roms;

import java.util.logging.Logger;

public class OrderTicketId extends Id{
    protected static final Logger logger = Logger.getLogger("roms");
    
    public OrderTicketId(String id) {
        super(id);
        logger.fine("new OrderTicketId instance creation");
    }
}