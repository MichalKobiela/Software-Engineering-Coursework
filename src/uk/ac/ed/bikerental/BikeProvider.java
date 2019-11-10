
package uk.ac.ed.bikerental;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class BikeProvider {
    private String name;
    private Location shopAddress;
    private String phoneNumber;
    private TimeRange openingHours;
    private Map<BikeType, Double> dailyRentalPrice;
    private double depositRate;
    private Collection<Bike> bikes;
    private Collection<BikeProvider> partners;
    private Collection<Booking> bookings;
    
    
    public BikeProvider(String name, String postCode, String address, String phoneNumber, TimeRange openingHours) {
        this.name = name;
        this.shopAddress= new Location(postCode, address);
        this.phoneNumber = phoneNumber;
        this.openingHours=openingHours;
    }
    public void registerDepositReturn(long bookingId){
        //ToDo
    }
    
    public void registerBikeReturnToPartner(BikeProvider partner, long bikeId) {
        // ToDo
    }
    
    public void registerBikeReturn(long bikeId) {
        for(Bike bike : bikes) {
            if(bike.getBikeId()==bikeId) {
                bike.setInStore(true);
            }
        }
    }
    public Optional<Quote> getQuote(Map<BikeType, Integer> bikeMap, DateRange dateRange){
        return null;
        //Todo
    }
    
    public boolean isPartner(BikeProvider partner) {
        return partners.contains(partner);
    }
    
    public boolean addPartner(BikeProvider partner) {
        return partners.add(partner);
    }
    
    public boolean addBooking(Booking booking) {
        return bookings.add(booking);
    }
    public void makeBookingCompleted(long bookingId){
        //Todo
    }
    
}
