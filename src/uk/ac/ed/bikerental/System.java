package uk.ac.ed.bikerental;

import java.util.Collection;
import java.util.Map;

public class System {
    private Collection<Customer> customers;
    private Collection<BikeProvider> bikeProviders;
    private Collection<BikeType> bikeTypes;
    
    
    public Collection<Quote> getQuotes(Map<BikeType, Integer> bikes, DateRange dateRange, Location location) {
        //TODO
        return null;
    }
    public Booking bookQuote(Quote quote, Customer customer, boolean storeCollection) {
        //TODO
        return null;
    }
    public boolean isCustomer(Customer customer) {
        return customers.contains(customer);
    }
    public boolean confirmBooking(Booking booking) {
        assert false;
        return false;
        //TODO No idea what is this function doing and why we have it 
    }
    public String displayConfirmation(Booking booking) {
        //TODO
        return "";
    }
}
