package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class BikeRentalSystem {
    private Collection<Customer> customers;
    private Collection<BikeProvider> bikeProviders;
    private Collection<BikeType> bikeTypes;
    
    //Singleton class
    private static BikeRentalSystem instance = new BikeRentalSystem();
    
    public static BikeRentalSystem getInstance() {
        return instance;
    }
    
    private BikeRentalSystem() {
        customers= new HashSet<Customer>();
        bikeProviders= new HashSet<BikeProvider>();
        bikeTypes = new HashSet<BikeType>();
    }
    public boolean addBikeProvider(BikeProvider bikeProvider) {
        return bikeProviders.add(bikeProvider);
    }
    public boolean addCusomter(Customer customer) {
        return customers.add(customer);
    }
    public boolean addBikeType(BikeType bikeType) {
        return bikeTypes.add(bikeType);
    }
    public boolean addBikeType(String name, BigDecimal replacementValue) {
        return bikeTypes.add(new BikeType(name, replacementValue));
    }
    public Optional<BikeType> findBikeType(String name) {
        for(BikeType type : bikeTypes) {
            if(type.getName().equals(name)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
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
        return result;
    }
    public Booking bookQuote(Quote quote, Customer customer, boolean storeCollection) {
        //TODO reserve bikes maybe useful, we need to implement at this point deliverable stuff in bike 
        // Probably it will be sufficient if we just add some attribute to bike e.g. beingDelivered and upadate it with
        // those methods
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
