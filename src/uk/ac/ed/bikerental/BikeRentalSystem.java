package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class BikeRentalSystem {
    private Collection<Customer> customers;
    private Collection<BikeProvider> bikeProviders;
    private Collection<BikeType> bikeTypes;
    private Collection<Long> bookingIds;
    //Singleton class
    private static BikeRentalSystem instance = new BikeRentalSystem();
    
    public static BikeRentalSystem getInstance() {
        return instance;
    }
    
    private BikeRentalSystem() {
        customers= new HashSet<Customer>();
        bikeProviders= new HashSet<BikeProvider>();
        bikeTypes = new HashSet<BikeType>();
        bookingIds = new HashSet<Long>();
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
    
    public boolean addBookingId(long bookingId) {
        return bookingIds.add(bookingId);
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
    public Booking bookQuote(Quote quote, Customer customer, boolean storeCollection, DeliveryService deliveryService) {
        long bookingId = generateBookingId(quote, customer);
        String orderSummary = "Order Summary:\nCustomer: " + customer.getFirstName() + " " + customer.getLastName() + "\n";
        orderSummary += "Booking ID: " + bookingId + "\n";
        orderSummary += "Date Range: " + quote.getDateRange().getStart() + " to " + quote.getDateRange().getEnd() + "\n";
        orderSummary += "Bike Provider: " + quote.getBikeProvider().getName() + "\n";
        orderSummary += "Total Price: " + quote.getTotalPrice() + "\n";
        orderSummary += "Deposit: " + quote.getDeposit() + "\n";
        orderSummary += "In Store Collection: " + storeCollection;
  
        Booking booking = new Booking(bookingId, quote, customer, storeCollection, customer.getAddress(), orderSummary, false, false, false);
        
        booking.getQuote().getBikeProvider().addBooking(booking);
  
        if(!storeCollection) {
            for(Bike bike : quote.getBikes()) {
                DeliveryServiceFactory.getDeliveryService().scheduleDelivery(bike, quote.getBikeProvider().getLocation(), customer.getAddress(), quote.getDateRange().getStart());      
            }
        }
        
        for(Bike bike : quote.getBikes()) {
            bike.getDatesReserved().add(quote.getDateRange());
        }
        
        return booking;
    }
    
    public long generateBookingId(Quote quote, Customer customer) {
        long bookingId = 0;
        //TODO need to come up with some way to make a bookingid and check that it's unique
        return bookingId;
    }
    
    public boolean isCustomer(Customer customer) {
        return customers.contains(customer);
    }
    public boolean confirmBooking(Booking booking) {
        //some i/o for displayConfirmation, where the customer can then select yes or no
        assert false;
        return false;
        //TODO No idea what is this function doing and why we have it 
    }
    public String displayConfirmation(Booking booking) {
        //TODO some function to the output of the system that displays confirmation
        String confirmation = "";
        confirmation += "Please confirm your booking information is correct:\n";
        confirmation += booking.toString();
               
        return confirmation;
    }
    public static void main(String[] args) {
        // I will change it into the test
        BikeProvider provider1 = new BikeProvider("Provider1", "EH1 2EQ", "High Street 75", "07789654", new TimeRange(9,0,16,0), new BigDecimal("0.12"));
        BikeProvider provider2 = new BikeProvider("Provider2", "EH1 7AE", "Nice Street 75", "07989659", new TimeRange(9,0,15,0), new BigDecimal("0.15"));
        BikeProvider provider3 = new BikeProvider("Provider3", "G1 7NB", "Narrow Street 75", "07189657", new TimeRange(9,0,17,30), new BigDecimal("0.4"));
        BigDecimal high = new BigDecimal(1500);
        BigDecimal medium = new BigDecimal(300);
        BigDecimal low = new BigDecimal("50.12");
        
        BikeType type1 = new BikeType("type1",high);
        BikeType type2 = new BikeType("type1",medium);
        BikeType type3 = new BikeType("type1",low);
        Bike bike1 = new Bike(1,type1);
        Bike bike2 = new Bike(2,type1);
        Bike bike3 = new Bike(3,type1);
        Bike bike4 = new Bike(4,type1);
        Bike bike5 = new Bike(5,type2);
        Bike bike6 = new Bike(6,type2);
        Bike bike7 = new Bike(7,type3);
        Bike bike8 = new Bike(8,type3);
        Bike bike9 = new Bike(9,type3);
        Bike bike10 = new Bike(10,type3);
        provider1.setDailyRentalPrice(type1, new BigDecimal(10));
        provider1.setDailyRentalPrice(type2, new BigDecimal(20));
        provider1.setDailyRentalPrice(type3, new BigDecimal("25.5"));
        provider1.addBike(bike1);
        provider1.addBike(bike2);
        provider1.addBike(bike3);
        provider1.addBike(bike4);
        provider1.addBike(bike5);
        provider1.addBike(bike6);
        provider1.addBike(bike7);
        provider1.addBike(bike8);
        provider1.addBike(bike9);
        provider1.addBike(bike10);
        
        Bike bike1a = new Bike(1,type1);
        Bike bike2a = new Bike(2,type1);
        Bike bike3a = new Bike(3,type1);
        Bike bike4a = new Bike(4,type2);
        
        provider2.setDailyRentalPrice(type1, new BigDecimal(15));
        provider2.setDailyRentalPrice(type2, new BigDecimal(25));
        provider2.setDailyRentalPrice(type3, new BigDecimal("30.5"));
        provider2.addBike(bike1a);
        provider2.addBike(bike2a);
        provider2.addBike(bike3a);
        provider2.addBike(bike4a);
        
        Bike bike1b = new Bike(1,type1);
        provider3.setDailyRentalPrice(type1, new BigDecimal(5));
        provider3.setDailyRentalPrice(type2, new BigDecimal(12));
        provider3.setDailyRentalPrice(type3, new BigDecimal("32.5"));
        provider3.addBike(bike1b);
        
        HashMap<BikeType, Integer> bikeMap1=new HashMap<BikeType, Integer>();
        bikeMap1.put(type1, 2);
        bikeMap1.put(type2, 1);
        
        LocalDate date1 = LocalDate.of(2019, 11, 1);
        LocalDate date2 = LocalDate.of(2019, 11, 5);
        DateRange dateRange = new DateRange(date1, date2);
        BikeRentalSystem sys=BikeRentalSystem.getInstance();
        System.out.println(sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7")).size());
        Collection<Quote> quotes= sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7"));
        for(Quote quote: quotes) {
            System.out.println(quote.getTotalPrice());
            System.out.println(quote.getDeposit());
        }

        LocalDate date3 = LocalDate.of(2019, 11, 1);
        LocalDate date4 = LocalDate.of(2019, 11, 2); 

        DateRange overlappingDateRange = new DateRange(date3, date4);        
        bike1a.reserve(overlappingDateRange);
        bike2a.reserve(overlappingDateRange);
        // now provider2 is unable to to offer a quote so we should have only one offer
        System.out.println(sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7")).size());       
        
    }

    public boolean containsType(BikeType bikeType) {
        return bikeTypes.contains(bikeType);
    }
}
