package roms;

import java.util.logging.Logger;

public class MenuItem {
    private MenuItemId id;
    private String description;
    private Money price;
    protected static final Logger logger = Logger.getLogger("roms");
    
    public MenuItem(MenuItemId id,String description,Money price){
        logger.fine("new MenuItem instance creation");
        this.id=id;
        this.description=description;
        this.price=price;
    }
    
    public MenuItemId getMenuItemId() {
        logger.fine("Entry");
        return id;
    }

    public Money getPrice() {
        logger.fine("Entry");
        return price;
    }
    public void setPrice(Money price) {
        logger.fine("Entry");
        assert (price.toString().charAt(0)=='-') : "Negative price given";
        this.price = price;
    }
    public String getDescription() {
        logger.fine("Entry");
        return description;
    }
    public void setDescription(String description) {
        logger.fine("Entry");
        this.description = description;
    }
    
    

}
