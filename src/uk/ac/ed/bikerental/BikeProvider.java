
package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BikeProvider {
    private String name;
    private Location shopAddress;
    private String phoneNumber;
    private TimeRange openingHours;
    private Map<BikeType, BigDecimal> dailyRentalPrice;
    private BigDecimal depositRate;
    private Map<BikeType, Collection<Bike>> bikes;
    private Collection<BikeProvider> partners;
    private Collection<Booking> bookings;
    
    
    public BikeProvider(String name, String postCode, String address, String phoneNumber, TimeRange openingHours, BigDecimal depositRate) {
        this.name = name;
        this.shopAddress= new Location(postCode, address);
        this.phoneNumber = phoneNumber;
        this.openingHours=openingHours;
        this.depositRate = depositRate;
        BikeRentalSystem.getInstance().addBikeProvider(this);
        
    }
    
    
    public void registerDepositReturn(long bookingId){
        for(Booking booking : bookings) {
            if(booking.getBookingId() == bookingId) {
                booking.registerDepositReturn();
            }
        }
    }
    
    public void registerBikeReturnToPartner(BikeProvider partner, long bikeId) {
        partner.registerBikeReturn(bikeId);
    }
    
    
    public void registerBikeReturn(long bikeId) {
        for(BikeType key : bikes.keySet()) {
            for(Bike bike : bikes.get(key)) {
                if(bike.getBikeId() == bikeId) {
                    bike.setInStore(true);
                }
            }
        }
    }
    public Optional<Quote> getQuote(Map<BikeType, Integer> bikeMap, DateRange dateRange){
        assert !bikeMap.keySet().isEmpty();
        ArrayList<Bike> bikesToOffer = new ArrayList<Bike>();
        for(BikeType bikeType : bikeMap.keySet()) {
            int quantity = bikeMap.get(bikeType);
            Collection<Bike> bikesOfCurrentType = findBikes(bikeType, quantity, dateRange);
            if(bikesOfCurrentType.size() == quantity) {
                bikesToOffer.addAll(bikesOfCurrentType);
            }
            else {
                return Optional.empty();
            }
        }
        BigDecimal totalPrice = calculatePrcie(bikesToOffer);
        Quote quote = new Quote(this, dateRange, totalPrice, totalPrice.multiply(depositRate), bikesToOffer); //TODO deposit and totalPrice
        return Optional.of(quote);
    }
    
    private BigDecimal calculatePrcie(Collection<Bike> bikes) {
            BigDecimal price = new BigDecimal(0);
            for(Bike bike : bikes) {
                BigDecimal priceOfBike = dailyRentalPrice.get(bike.getType());
                price.add(priceOfBike);
            }
            return price;
    }



    private Collection<Bike> findBikes(BikeType bikeType, int quantity, DateRange dateRange) {
        Collection<Bike> result = new ArrayList<Bike>();
        Collection<Bike> listOfBikes = bikes.get(bikeType);
        int counter = 0;
        for(Bike bike : listOfBikes) {
           if(bike.isAvailable(dateRange)) {
               result.add(bike);
               counter++;
               if(counter == quantity) {
                   break;
               }
           }
        }
        return result;
        
    }


    public boolean isPartner(BikeProvider partner) {
        assert partner != null;
        return partners.contains(partner);
    }
    
    public boolean addPartner(BikeProvider partner) {
        assert partner != null;
        return partners.add(partner);
    }
    
    public boolean addBooking(Booking booking) {
        assert booking != null;
        return bookings.add(booking);
    }
    public void makeBookingCompleted(long bookingId){
        for(Booking booking : bookings) {
            if(booking.getBookingId() == bookingId) {
                booking.makeCompleted();
            }
        }
    }
    public void addBike(Bike bike) { 
        BikeType type = bike.getType();
        if(!bikes.keySet().contains(type)) {
            bikes.put(type, new ArrayList<Bike>());
        }
        bikes.get(type).add(bike);
    }
    public void addBike(long bikeid, BikeType type) { 
        this.addBike(new Bike(bikeid, type));
    }
    public void addBikeType(String name, BigDecimal replecementValue) { 
        BikeRentalSystem.getInstance().addBikeType(name, replecementValue);
    }
    public void addBike(long bikeid, String bikeType) { 
        Optional<BikeType> typeFromSystem = BikeRentalSystem.getInstance().findBikeType(bikeType);
         if(typeFromSystem.isPresent()) {
             this.addBike(new Bike(bikeid, typeFromSystem.get())); 
         }
         else {
             System.out.println("This type of bike is not in database. Please add it first using addBikeType()");
         }
    }

    public Location getLocation() {
        return shopAddress;
    }
    
    public String getName() {
        return name;
    }
    
    
    
}
