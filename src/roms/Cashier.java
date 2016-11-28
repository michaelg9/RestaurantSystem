package roms;

import java.util.logging.Logger;

public class Cashier {
    /**
     * Mediator class that coordinates the pay bill use case.
     * Interacts directly with the table's card reader,
     * the table's receipt printer and the Bank client. 
     */
    
    private CardReader cardReader;
    private BankClient bankClient;
    private ReceiptPrinter receiptPrinter;
    protected static final Logger logger = Logger.getLogger("roms");
    
    public void setCardReader(CardReader cardReader) {
        logger.fine("Entry");
        this.cardReader = cardReader;
    }

    public void setBankClient(BankClient bankClient) {
        logger.fine("Entry");
        this.bankClient = bankClient;
    }

    public void setReceiptPrinter(ReceiptPrinter receiptPrinter) {
        logger.fine("Entry");
        this.receiptPrinter = receiptPrinter;
    }
    
    /**
     * this is the method that triggers the use case, when called by TableDisplay.
     * a Money argument is passed which is the total order amount, taken by the
     * table's ticket
     */
    public void pay(Money total){
        //read card details from the customer by non-triggered input method
        String cardDetails=cardReader.waitForCardDetails();
        //send card details and total amount to bankCient for authorisation. 
        //makePayment output event using card details and total follow by input event 
        //acceptAuthorizationCode that returns the payment authorisation code
        String authorizationCode=bankClient.authorisePayment(cardDetails, total);
        //print the receipt which includes total amount and the authorisation code. Output event. 
        receiptPrinter.printReceipt(total, authorizationCode);
        logger.fine("Payment was made successfully");
    }

}
