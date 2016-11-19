package roms;

import java.util.logging.Logger;

public class OfficeOperations {

    private Menu menu = new Menu();
    protected static final Logger logger = Logger.getLogger("roms");
    public OfficeOperations(){
        logger.fine("new OfficeOperations instance creation");
    }
    
    public Menu getMenu(){
        //Retrieves the Menu instance
        logger.fine("Entry");
        return menu;
    }
    public void addMenuItem(MenuItem item){
        logger.fine("Entry");
        menu.addNewItem(item);
    }
    public void deleteExistingItem(MenuItemId itemId){
        logger.fine("Entry");
        menu.deleteExistingItem(itemId);
    }
    /*
    public AuthorizationCode charge(CardDetails details, Money amount){

    }
    */
}
