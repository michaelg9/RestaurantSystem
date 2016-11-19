/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
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
    
    public Menu(){}
    
    public List<String> toStrings() {
        //converts the menu to a string for printing
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
        for(int i=0;i<catalogue.size();i++){
            //Add the Menu Item in the correct lexicographic ordered position
            String currentItemId=catalogue.get(i).getMenuItemId().getId();
            String addItemId = item.getMenuItemId().getId();
            int comparison = currentItemId.compareTo(addItemId);
            //if item already exists, exit with appropriate error message
            assert (comparison!=0) : "Attempt to add already existing item";
            if(comparison > 0){
                catalogue.add(i,item);
                break;
            }
        }
    }
    
    public void deleteExistingItem(MenuItemId id){
        //precondition: item doesn't already exist in the menu
        boolean exists = false;
        for (MenuItem item:catalogue){
            if (item.getMenuItemId().equals(id)){
                catalogue.remove(item);
                exists=true;
                break;
            }
        }
        //if item already exists, exit with appropriate error message
        assert (!exists) : "Attempt to remove non-existing item";
    }
}
