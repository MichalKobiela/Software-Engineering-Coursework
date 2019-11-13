package uk.ac.ed.bikerental;

import java.util.Collection;
import java.util.Optional;

public class Booking {
    private long bookingId;
    private Quote quote;
    private Customer customer;
    private boolean storeCollection;
    private Location customerAddress;
    private String orderSummary;
    private boolean completed;
    private Deposit deposit;
    
       
    public Booking(long bookingId, Quote quote, Customer customer, boolean storeCollection, Location customerAddress,
            String orderSummary) {
        super();
        this.bookingId = bookingId;
        this.quote = quote;
        this.deposit= quote.getDeposit();
        this.customer = customer;
        this.storeCollection = storeCollection;
        this.customerAddress = customerAddress;
        this.orderSummary = orderSummary;
        this.completed = false;
        
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void registerDepositReturn() {
        deposit.depositReturn();
    }
    
    public void makeCompleted() {
        completed = true; 
    }
    
    public boolean isCompleted() {
        return completed;
    }

    public String toString() {
        String stringOut = "";
        
        stringOut += "BookingId: " + bookingId + "\nCustomer Name: " + customer.getFirstName() + " " + customer.getLastName() + "\n";
        stringOut += "Order Summary: " + orderSummary + "\n";
        //stringOut += "Completed: " + completed;       
        
        return stringOut;
    }
    
    public long getBookingId() {
        return bookingId;
    }

    public void sendConfirmation() {
        //TODO supposed to send some confirmation but given that we don't have anything to send the confirmation to we have left it empty
    }
    
    public Quote getQuote() {
        return quote;
    }

    public void depositPaid() {
        this.deposit.depositPaid();
        
    }

    public void bikesGivenToCustomer() {
        Collection<Bike> bikes = quote.getBikes();  
        for (Bike bike : bikes) {
            Collection<DateRange> datesReserved = bike.getDatesReserved();
            bike.setInStore(false);      
        }      
    }
   
}
