package roms;
/**
 * Mediator class that coordinates the use cases that belong in the kitchen
 * Coordinates ShowRack, IndicateItemReady, CancelReadyUpLight use cases
 * 
 */

public class KitchenCoordinator {
    
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
     * coordinating method called by the clock on regular intervals
     * used to refresh the screen with the orders and their waiting times
     */
    public void refreshOrderRack(){
        kitchenDisplay.displayRack(orderRack);
    }
    
    /**
     * Coordinating method called by the kitchen display
     * used to indicate an item in an order in the rack is ready
     * and print the ticket if it's the first item
     * and turn on the passlight if it's the last item
     */
     
    public void indicateItemReady(int ticketNumber, String menuID){
        Ticket ticket=orderRack.getTicket(ticketNumber);
        if (!ticket.isFirstItemReady()){
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
        passLight.switchOff();
    }

}
