package roms;

import java.util.logging.Logger;

public class OrderItem {
    private MenuItem item;
    private int quantity=1;
    private int quantityReady=0;
    protected static final Logger logger = Logger.getLogger("roms");
    
    public OrderItem(MenuItem item){
        logger.fine("Entry");
        this.item=item;
    }

    public int getQuantityReady() {
        logger.fine("Entry");
        return quantityReady;
    }

    public void incrementQuantityReady() {
        logger.fine("Entry");
        quantityReady++;
    }

    public MenuItem getItem() {
        logger.fine("Entry");
        return item;
    }
    
    public int getQuantity() {
        logger.fine("Entry");
        return quantity;
    }
    
    public void incrementQuantity(int quantity) {
        logger.fine("Entry");
        this.quantity += quantity;
    }

    public boolean equals(OrderItem item){
        logger.fine("Entry");
        return item.getItem().equals(this.getItem());
    }
}
