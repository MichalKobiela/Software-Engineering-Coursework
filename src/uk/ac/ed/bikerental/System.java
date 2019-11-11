package uk.ac.ed.bikerental;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class System {
    private Collection<Customer> customers;
    private Collection<BikeProvider> bikeProviders;
    private Collection<BikeType> bikeTypes;
    
    //Singleton class
    private static System instance = new System();
    
    public static System getInstance() {
        return instance;
    }
    
    private System() {
        customers= new HashSet<Customer>();
        bikeProviders= new HashSet<BikeProvider>();
        bikeTypes = new HashSet<BikeType>();
    }
    public boolean addBikeProvider(BikeProvider bikeProvider) {
        return bikeProviders.add(bikeProvider);
    }
    public Collection<Quote> getQuotes(Map<BikeType, Integer> bikes, DateRange dateRange, Location location) {
        Collection<Quote> result = new HashSet<Quote>();
        for(BikeProvider bikeProvider : bikeProviders) {
            if(bikeProvider.getLocation().isNearTo(location)) {
                Optional<Quote> potentialOffer = bikeProvider.getQuote(bikes, dateRange);
                if(potentialOffer.isPresent()) {
                    result.add(potentialOffer.get());
                }
            }
        }
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
