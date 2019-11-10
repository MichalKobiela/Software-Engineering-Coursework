
package uk.ac.ed.bikerental;

import java.util.Collection;
import java.util.Map;

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
    
    
    public BikeProvider(String name, String postCode, String address) {
        super();
        this.name = name;
        this.shopAddress= new Location(postCode, address);
    }
    
}
