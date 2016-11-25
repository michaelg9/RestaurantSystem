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
 * Internally, it contains a list MenuItems and provides 
 * methods for modifying the Menu itself.
 * 
 */
public class Menu {
    /**
     *      Catalogue that holds all the Menu Items in the current Menu
     *      in an Array List
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
     *    @return List&lt;String&gt - The list of the menu items;
     */
    public List<String> toStrings() {
        //converts the menu to a string for printing
        logger.fine("Entry");
        String[] stringArray=new String[catalogue.size()*3];
        for (int i=0;i<stringArray.length;i+=3){
            MenuItem item= catalogue.get(i/3);
            stringArray[i]=item.getId();
            stringArray[i+1]=item.getDescription();
            stringArray[i+2]=item.getPrice().toString();
        }
        List<String> ss = new ArrayList<String>();
        ss.addAll(Arrays.asList(stringArray));
        return ss;
    }
    /**
     *      Retrieves a menu item that corresponds to the menu item id passed
     *      as a parameter
     * 
     *      @param MenuItemId - The menu item id of the item to be retrieved
     *      @return MenuItem - The menu item that corresponds to the id passed (null if non existent)
     */
    public MenuItem getItem(String MenuItemId){
        logger.fine("Entry");
        boolean found = false;
        MenuItem menuItem=null;
        for(MenuItem item:catalogue){
            if(item.getId().equals(MenuItemId)){
                found = true;
                menuItem = item;
                break;
            }
        }
        assert(found):"Item not found in menu"; 
        //In the case that the menu item id is not one of the items in the menu, then the an Assertion Error is thrown
        return menuItem;
    }
    /**
     *      Adds a new menu item passed as a parameter, in the menu.
     *      If the item to be added is already in the menu (determined by the menu item id),
     *      then an Assertion Error is thrown.
     *       
     *      @param item - The new menu item to be added
     */
    public void addNewItem(MenuItem item){
        //precondition: item doesn't already exist in the menu
        logger.fine("Entry");
        if(catalogue.isEmpty()){
            catalogue.add(item);
            logger.fine("Item added to the Menu successfully");
            return;
        }
        boolean added=false;
        for(int i=0;i<catalogue.size();i++){
            //Add the Menu Item in the correct lexicographic ordered position
            String currentItemId=catalogue.get(i).getId();
            String addItemId = item.getId();
            int comparison = currentItemId.compareTo(addItemId);
            if (comparison==0){
                //item already exists, updating it
                catalogue.set(i, item);
                added=true;
                logger.fine("Already existing item updated in the Menu successfully");
                break;
            }
            else if(comparison > 0){
                //current item is the first greater than the current
                //we put the new item in i and shift everything else right
                catalogue.add(i,item);
                added=true;
                logger.fine("New item added to the Menu successfully");
                break;
            }
        }
        if(!added){
            catalogue.add(item);
            logger.fine("New item added to the Menu successfully");
        }
    }
    /**
     *      Deletes the menu item determined by the menu item id 
     *      passed as a parameter.
     *      If the menu item id passed is not matching a item already in the menu,
     *      then an Assertion Error is thrown 
     *      
     *      @param itemId - The item id of the menu item to be deleted
     */
    public void deleteExistingItem(String itemId){
        //precondition: item doesn't already exist in the menu
        logger.fine("Entry");
        boolean exists = false;
        for (MenuItem item:catalogue){
            if (item.getId().equals(itemId)){
                catalogue.remove(item);
                logger.fine("Item deleted from the Menu successfully");
                exists=true;
                break;
            }
        }
        //if item already exists, exit with appropriate error message
        assert (exists) : "Attempt to remove nonexisting item";
    }
}
