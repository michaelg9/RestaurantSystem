package roms;

public class MenuItem {
    private MenuItemId id;
    private String description;
    private Money price;

    public MenuItem(MenuItemId id,String description,Money price){
        this.id=id;
        this.description=description;
        this.price=price;
    }
    
    public MenuItemId getMenuItemId() {
        return id;
    }

    public Money getPrice() {
        return price;
    }
    public void setPrice(Money price) {
        //TODO: assert positive
        assert (price.toString().charAt(0)=='-') : "Negative price given";
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    

}
