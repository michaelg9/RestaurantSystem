package roms;

import java.util.logging.Logger;

/**
 * Mediator class that coordinates the use cases that belong in the kitchen
 * Coordinates SubmitOrder, ShowRack, IndicateItemReady, CancelReadyUpLight use cases
 * Triggered by KitchenDisplay for the IndicateItemReady,
 * by TableDisplay for the SubmitOrder use case,
 * by the Clock for the ShowRack use case,
 * by the PassButton for the CancelReadyUpLight
 */

public class KitchenCoordinator {
    private static final Logger logger = Logger.getLogger("roms");
    private KitchenDisplay kitchenDisplay;
    private Rack orderRack;
    private TicketPrinter ticketPrinter;
    private PassLight passLight;

    public void setPassLight(PassLight passLight) {
        this.passLight = passLight;
    }

    public void setTicketPrinter(TicketPrinter ticketPrinter) {
        this.ticketPrinter = ticketPrinter;
    }

    public void setOrderRack(Rack orderRack) {
        this.orderRack = orderRack;
    }

    public void setKitchenDisplay(KitchenDisplay kitchenDisplay) {
        this.kitchenDisplay = kitchenDisplay;
    }
    
    /**
     * coordinating method called by the TableDisplay when the customer wants to submit the order
     * used to put the pending order in the rack to be displayed in the kitchen
     */
    public void submitOrder(Ticket ticket){
        logger.fine("Submitting order...");
        ticket.setId(orderRack.getCounter());
        ticket.setDate(Clock.getInstance().getDateAndTime());
        orderRack.submitOrder(ticket);
    }
    
    
    /**
     * coordinating method called by the clock on regular intervals
     * used to refresh the screen with the orders and their waiting times
     */
    public void refreshOrderRack(){
        logger.fine("Refreshing rack");
        kitchenDisplay.displayRack(orderRack);
    }
    
    /**
     * Coordinating method called by the kitchen display
     * used to indicate an item in an order in the rack is ready
     * and print the ticket if it's the first item
     * and turn on the passlight if it's the last item
     */
     
    public void indicateItemReady(int ticketNumber, String menuID){
        logger.fine("Indicating item ready...");
        Ticket ticket=orderRack.getTicket(ticketNumber);
        if (!ticket.isFirstItemReady()){
            //No items have been indicated ready yet, thus the order ticket has to be printed
            ticketPrinter.printTicket(ticket);
        }
        orderRack.indicateItemReady(ticketNumber, menuID);
        if (ticket.isFinished()){
            //remove the ticket from the pending tickets list in the rack and 
            orderRack.removeOrder(ticket.getId());
            //switch the passlight on
            passLight.switchOn();
        }
    }
    
    /**
     * coordinating method called by the passbutton 
     * used to turn the passlight off
     */
    public void cancelReadyUp() {
        logger.fine("Cancelling ready up light");
        passLight.switchOff();
    }

}
