package roms;

public class MenuItem {
    private String id;
    private String description;
    private Money price;
    
    public MenuItem(String id,String description,Money price){
        //precondition: price given is non-negative
        assert (price.toString().charAt(0)!='-') : "Negative price given";
        this.id=id;
        this.description=description;
        this.price=price;
    }
    
    public String getId() {
        return id;
    }
    public Money getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
    
}
