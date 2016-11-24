/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class for Menu objects.
 * 
 * Menu objects are used to describe the menu of the restaurant.
 * Internally, it contains a list MenuItems as well as provides 
 * methods for modifying the Menu.
 * 
 * @author pbj
 *
 */
public class Menu {
    /**
     * Format menu as list of strings, with, per menu item, 3 strings for 
     * respectively:
     * - MenuID
     * - Description
     * - Price
     * 
     * Items are expected to be ordered by MenuID.
     * 
     * An example list is:
     * 
     * "D1", "Wine",        "2.50",
     * "D2", "Soft drink",  "1.50",
     * "M1", "Fish",        "7.95",
     * "M2", "Veg chili",   "6.70"
     * 
     * These lists of strings are used by TableDisplay and TicketPrinter
     * to produce formatted ticket output messages.
     * 
     * @return
     */
    private ArrayList<MenuItem> catalogue=new ArrayList<MenuItem>(); 
    protected static final Logger logger = Logger.getLogger("roms");
    /** 
     *    Creates a new Menu instance.
     *    The Menu created has an empty list of MenuItems.
     *    
     */
    public Menu(){
        logger.fine("new Menu instance creation");
    }
    /** 
     *    Creates an ArrayList&lt;String&gt; containing all the current menu items
     *    in the form: "D1", "Wine", "2.50",
     *                 "D2", "Soft drink",  "1.50",
     *                 "M1", "Fish",        "7.95",
     *                 "M2", "Veg chili",   "6.70".
     *    Where the items are multiples of three and each triple 
     *    describes ID, Description, Price, in order.
     *    
     *    @return List&lt;String&gt;
     */
    public List<String> toStrings() {
        //converts the menu to a string for printing
        logger.fine("Entry");
        String[] stringArray=new String[catalogue.size()*3];
        for (int i=0;i<stringArray.length;i+=3){
            MenuItem item= catalogue.get(i);
            stringArray[i]=item.getMenuItemId().getId();
            stringArray[i+1]=item.getDescription();
            stringArray[i+2]=item.getPrice().toString();
        }
        List<String> ss = new ArrayList<String>();
        ss.addAll(Arrays.asList(stringArray));
        return ss;
    }
    
    public void addNewItem(MenuItem item){
        //precondition: item doesn't already exist in the menu
        logger.fine("Entry");
        for(int i=0;i<catalogue.size();i++){
            //Add the Menu Item in the correct lexicographic ordered position
            String currentItemId=catalogue.get(i).getMenuItemId().getId();
            String addItemId = item.getMenuItemId().getId();
            int comparison = currentItemId.compareTo(addItemId);
            //if item already exists, exit with appropriate error message
            assert (comparison!=0) : "Attempt to add already existing item";
            if(comparison > 0){
                catalogue.add(i,item);
                logger.fine("item added to the Menu successfully");
                break;
            }
        }
    }
    
    public void deleteExistingItem(MenuItemId itemId){
        //precondition: item doesn't already exist in the menu
        logger.fine("Entry");
        boolean exists = false;
        for (MenuItem item:catalogue){
            if (item.getMenuItemId().equals(itemId)){
                catalogue.remove(item);
                logger.fine("item deleted from the Menu successfully");
                exists=true;
                break;
            }
        }
        //if item already exists, exit with appropriate error message
        assert (!exists) : "Attempt to remove non-existing item";
    }
}
