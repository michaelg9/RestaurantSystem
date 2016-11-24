package roms;

import java.util.logging.Logger;

public class OrderItemId extends Id {
    protected static final Logger logger = Logger.getLogger("roms");

    public OrderItemId(String id) {
        super(id);
        logger.fine("Entry");
    }

}
