package roms;

import java.util.logging.Logger;

public class MenuItemId extends Id {
    protected static final Logger logger = Logger.getLogger("roms");
    
    public MenuItemId(String id) {
        super(id);
        logger.fine("new MenuItemId instance creation");
    }

}
