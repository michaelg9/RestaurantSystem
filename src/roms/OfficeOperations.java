package roms;

import java.util.logging.Logger;

/**
 * Mediator class for the ShowMenu use case. Interacts with officeKVM objects
 * and Table Displays when they need to interact with the menu Triggered by
 * officeKVM and Table Display objects
 */
public class OfficeOperations {

    private Menu menu = new Menu();
    protected static final Logger logger = Logger.getLogger("roms");

    public OfficeOperations() {
        logger.fine("new OfficeOperations instance creation");
    }

    public Menu getMenu() {
        // Retrieves the Menu instance
        return menu;
    }

    // adds a MenuItem to the menu
    public void addMenuItem(MenuItem item) {
        logger.fine("Entry");
        menu.addNewItem(item);
    }

    // deletes a MenuItem from the menu
    public void deleteExistingItem(String itemId) {
        logger.fine("Entry");
        menu.deleteExistingItem(itemId);
    }

}
