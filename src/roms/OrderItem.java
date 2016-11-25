package roms;

import java.util.logging.Logger;

public class OrderItem {
    private MenuItem item;
    private int quantity=1;
    private int quantityReady=0;
    protected static final Logger logger = Logger.getLogger("roms");
    
    public OrderItem(MenuItem item){
        this.item=item;
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
    
    public void incrementQuantity(int quantity) {
        this.quantity += quantity;
    }

    public boolean equal(OrderItem item){
        return item.getItem().equals(this.getItem());
    }
}
