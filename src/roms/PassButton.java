/**
 * 
 */
package roms;

/**
 * Model of push button at pass for cancelling (switching off) the pass 
 * ready-up light.
 * 
 * @author pbj
 *
 */
public class PassButton extends AbstractInputDevice {

    
    /**
     * 
     * @param instanceName  
     */
    public PassButton(String instanceName) {
        super(instanceName);   
    }
    
    
    /** 
     *    Select device action based on input event message
     *    
     *    @param e
     */
    @Override
    public void receiveEvent(Event e) {
        
        if (e.getMessageName().equals("press") 
                && e.getMessageArgs().size() == 0) {
            
            press();
            
       } else {
            super.receiveEvent(e);
        } 
    }

    /*
     ***********************************************************************
     * BEGIN MODIFICATION AREA
     ***********************************************************************
     *  
     * Add private field(s) for associations to classes that PassButton
     * objects need to send messages to. 
     * 
     * Add public setters for these fields.
     * 
     * Complete the method for handling trigger input events.
     * 
     */

    /*
     * FIELD(S) AND SETTER(S) FOR MESSAGE DESTINATIONS
     */
    //Pass button sends a message to Pass light through
    //the kitchen coordinator mediator class
    private KitchenCoordinator kitchenCoordinator;
    public void setKitchenCoordinator(KitchenCoordinator kitchenCoordinator){
        this.kitchenCoordinator=kitchenCoordinator;
    }

    /*
     * SUPPORT FOR TRIGGER INPUT MESSAGES
     */
    public void press() {
        logger.fine(getInstanceName());
        kitchenCoordinator.cancelReadyUp();
    }
    
    /*
     * Put all class modifications above.
     **********************************************************************
     * END MODIFICATION AREA
     *********************************************************************
     */
    
 }
