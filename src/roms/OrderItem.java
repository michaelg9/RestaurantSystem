package roms;

import java.util.logging.Logger;

public class OrderItem {
    private OrderItemId id;
    private MenuItem item;
    private int quantity;
    private int quantityReady=0;
    protected static final Logger logger = Logger.getLogger("roms");
    
    public OrderItem(OrderItemId id,MenuItem item,int quantity){
        this.id=id;
        this.item=item;
        this.quantity=quantity;
    }

    public int getQuantityReady() {
        return quantityReady;
    }

    public void incrementQuantityReady() {
        quantityReady++;
    }

    public OrderItemId getId() {
        return id;
    }

    public MenuItem getItem() {
        return item;
    }
    
    public int getQuantity() {
        return quantity;
    }

}
