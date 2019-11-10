package uk.ac.ed.bikerental;

import java.util.Collection;

public class Booking {
    private long bookingId;
    private Quote quote;
    private Customer customer;
    private boolean storeCollection;
    private String customerAddress;
    private String orderSummary;
    private boolean completed;
    private boolean depositPaid;
    private boolean depositReturned;
    
       
    public Booking(long bookingId, Quote quote, Customer customer, boolean storeCollection, String customerAddress,
            String orderSummary, boolean completed, boolean depositPaid, boolean depositReturned) {
        super();
        this.bookingId = bookingId;
        this.quote = quote;
        this.customer = customer;
        this.storeCollection = storeCollection;
        this.customerAddress = customerAddress;
        this.orderSummary = orderSummary;
        this.completed = completed;
        this.depositPaid = depositPaid;
        this.depositReturned = depositReturned;
    }

    public void registerDepositReturn() {
        depositReturned = true;
    }
    
    public void makeCompleted() {
        //TODO for all Bike in Booking, clear dateRange, set in store to true, make completed true, deposit return to true
        Collection<Bike> bikes = quote.getBikes();
        for (Bike bike : bikes) {
            bike.setDatesReserved(null);
            bike.setInStore(true);      
        }
        depositReturned = true;
        completed = true; 
    }
    
    public String toString() {
        String stringOut = "";
        
        stringOut += "BookingId: " + bookingId + "\nCustomer Name: " + customer.getFirstName() + " " + customer.getLastName() + "\n";
        stringOut += "Order Summary: " + orderSummary + "\n";
        stringOut += "Completed: " + completed;       
        
        return stringOut;
    }
    
    public long getBookingId() {
        return bookingId;
    }

    public void sendConfirmation() {
        //TODO supposed to send some confirmation but given that we don't have anything to send the confirmation to we have left it empty
    }
}
