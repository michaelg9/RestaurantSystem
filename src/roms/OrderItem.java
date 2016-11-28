package roms;

import java.util.logging.Logger;
/**
 * Class that represents a MenuItem after it is ordered
 *
 */

public class OrderItem {
    private MenuItem item;
    //quantity represents the amount of MenuItems ordered
    private int quantity=1;
    //quantityReady represents the amount of MenuItems prepered in the kitchen
    private int quantityReady=0;
    protected static final Logger logger = Logger.getLogger("roms");
    
    public OrderItem(MenuItem item){
        logger.fine("New Order item for the ticket created");
        this.item=item;
    }

    public int getQuantityReady() {
        return quantityReady;
    }
    //returns true if the the specific order item has been prepared in the kitchen
    public boolean isDone() {
        return quantity==quantityReady;
    }

    public void incrementQuantityReady() {
        logger.fine("Entry");
        //quantity ready should always be less than or equal to the quantity ordered
        quantityReady++;
        assert (quantityReady<=quantity): "Quantity prepared cannot be greater than ordered quantity";
        logger.fine("Ready quantity of item incremented");
    }

    public MenuItem getItem() {
        return item;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void incrementQuantity(int quantity) {
        this.quantity += quantity;
        logger.fine("Quantity of item incremented");
    }

    public boolean equals(OrderItem item){
        logger.fine("Entry");
        return item.getItem().equals(this.getItem());
    }
}
