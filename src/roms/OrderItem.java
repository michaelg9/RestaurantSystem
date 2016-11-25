package roms;

import java.util.logging.Logger;

public class OrderItem {
    private MenuItem item;
    private int quantity;
    private int quantityReady=0;
    protected static final Logger logger = Logger.getLogger("roms");
    
    public OrderItem(MenuItem item,int quantity){
        this.item=item;
        this.quantity=quantity;
    }

    public int getQuantityReady() {
        return quantityReady;
    }

    public void incrementQuantityReady() {
        quantityReady++;
    }

    public MenuItem getItem() {
        return item;
    }
    
    public int getQuantity() {
        return quantity;
    }

}
