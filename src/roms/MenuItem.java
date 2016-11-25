package roms;

import java.util.logging.Logger;

public class MenuItem {
    private String id;
    private String description;
    private Money price;
    protected static final Logger logger = Logger.getLogger("roms");
    
    public MenuItem(String id,String description,Money price){
        logger.fine("new MenuItem instance creation");
        //precondition: price given is non-negative
        assert (price.toString().charAt(0)!='-') : "Negative price given";
        this.id=id;
        this.description=description;
        this.price=price;
    }
    
    public String getId() {
        logger.fine("Entry");
        return id;
    }
    public Money getPrice() {
        logger.fine("Entry");
        return price;
    }
    public String getDescription() {
        logger.fine("Entry");
        return description;
    }
    
}
