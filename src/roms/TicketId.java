package roms;

import java.util.logging.Logger;

public class TicketId extends Id{
    protected static final Logger logger = Logger.getLogger("roms");
    
    public TicketId(String id) {
        super(id);
        logger.fine("new OrderTicketId instance creation");
    }
}