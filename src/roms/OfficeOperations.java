package roms;

public class OfficeOperations {

    private Menu menu = new Menu();
    
    public Menu getMenu(){
        //Retrieves the Menu instance
        return menu;
    }
    public void addMenuItem(MenuItem item){
        menu.addNewItem(item);
    }
    public void deleteExistingItem(MenuItemId itemId){
        menu.deleteExistingItem(itemId);
    }
    /*
    public AuthorizationCode charge(CardDetails details, Money amount){

    }
    */
}
