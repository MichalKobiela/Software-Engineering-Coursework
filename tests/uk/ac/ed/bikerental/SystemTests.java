package uk.ac.ed.bikerental;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class SystemTests {
    // You can add attributes here
    BikeProvider provider1, provider2, provider3;
    BigDecimal high, medium, low;
    BikeType type1, type2, type3;
    Bike bike1, bike2, bike3, bike4, bike5, bike6, bike7, bike8, bike9, bike10, bike1a, bike2a, bike3a, bike4a, bike5a, bike1b;
    LocalDate date1, date2, date3, date4;
    DateRange dateRange, overlappingDateRange;
    HashMap<BikeType, Integer> bikeMap1;
    static BikeRentalSystem sys;
    
    @BeforeEach
    void setUp() throws Exception {
        
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        
        // Put your test setup here
        BikeRentalSystem.resetForTest();
        sys=BikeRentalSystem.getInstance();
        provider1 = new BikeProvider("Provider1", "EH1 2EQ", "High Street 75", "07789654", new TimeRange(9,0,16,0), new BigDecimal("0.12"));
        provider2 = new BikeProvider("Provider2", "EH1 7AE", "Nice Street 75", "07989659", new TimeRange(9,0,15,0), new BigDecimal("0.15"));
        provider3 = new BikeProvider("Provider3", "G1 7NB", "Narrow Street 75", "07189657", new TimeRange(9,0,17,30), new BigDecimal("0.4"));
        high = new BigDecimal(1500);
        medium = new BigDecimal(300);
        low = new BigDecimal("50.12");
        
        type1 = new BikeType("type1",high);
        type2 = new BikeType("type2",medium);
        type3 = new BikeType("type3",low);
        bike1 = new Bike(1,type1);
        bike2 = new Bike(2,type1);
        bike3 = new Bike(3,type1);
        bike4 = new Bike(4,type1);
        bike5 = new Bike(5,type2);
        bike6 = new Bike(6,type2);
        bike7 = new Bike(7,type3);
        bike8 = new Bike(8,type3);
        bike9 = new Bike(9,type3);
        bike10 = new Bike(10,type3);
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
        
        bike1a = new Bike(1,type1);
        bike2a = new Bike(2,type1);
        bike3a = new Bike(3,type1);
        bike4a = new Bike(4,type2);
        
        provider2.setDailyRentalPrice(type1, new BigDecimal(15));
        provider2.setDailyRentalPrice(type2, new BigDecimal(25));
        provider2.setDailyRentalPrice(type3, new BigDecimal("30.5"));
        provider2.addBike(bike1a);
        provider2.addBike(bike2a);
        provider2.addBike(bike3a);
        provider2.addBike(bike4a);
        
        bike1b = new Bike(1,type1);
        provider3.setDailyRentalPrice(type1, new BigDecimal(5));
        provider3.setDailyRentalPrice(type2, new BigDecimal(12));
        provider3.setDailyRentalPrice(type3, new BigDecimal("32.5"));
        provider3.addBike(bike1b);
        
        bikeMap1=new HashMap<BikeType, Integer>();
        bikeMap1.put(type1, 2);
        bikeMap1.put(type2, 1);
        
        date1 = LocalDate.of(2019, 11, 1);
        date2 = LocalDate.of(2019, 11, 5);
        dateRange = new DateRange(date1, date2);

        date3 = LocalDate.of(2019, 11, 1);
        date4 = LocalDate.of(2019, 11, 2); 

        overlappingDateRange = new DateRange(date3, date4);
        
    }
    
    // TODO: Write system tests covering the three main use cases

    @Test
    void getQuotetest1() {
        //provider one and two can offer a quote for bikeMap1 so size must be 2;
        assertEquals(sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7")).size(), 2);

    }
    
    @Test
    void getQuotetest2() {
        
        bike1a.reserve(overlappingDateRange);
        bike2a.reserve(overlappingDateRange);
      //provider 2 now cannot offer a quote because bikes are reserved so the size will be 1;
        assertEquals(sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7")).size(), 1);
    }
    
    @Test
    void getQuotetest3() {
        
      //provider 3 is the only one that is near to this location;
        HashMap<BikeType, Integer> bikeMap2 = new HashMap<BikeType, Integer>();
        bikeMap2.put(type1,1);
        assertEquals(sys.getQuotes(bikeMap2, dateRange, new Location("G14 789", "Park Stret 7")).size(), 1);
    }
    
    @Test
    void getQuotetest4() {
        
        HashMap<BikeType, Integer> bikeMap2 = new HashMap<BikeType, Integer>();
        bikeMap2.put(type1,2);
        // None of providers can offer a quote
        assertEquals(sys.getQuotes(bikeMap2, dateRange, new Location("G14 789", "Park Stret 7")).size(), 0);
    }
    
    @Test
    void getQuotetest5() {
        
        BikeRentalSystem.resetForTest();
        // check if edge case have correct behavior
        sys.getQuotes(bikeMap1, dateRange, new Location("G14 789", "Park Stret 7"));
    }
    
    @Test
    void getQuotetest6() {
        
        BikeRentalSystem.resetForTest();
        HashMap<BikeType, Integer> bikeMap2 = new HashMap<BikeType, Integer>();
        bikeMap2.put(type3,1);
        // edge case when bike provider does not have type3 bikes
        sys.getQuotes(bikeMap2, dateRange, new Location("G14 789", "Park Stret 7"));
    }
    
    @Test
    void getQuotetestDeposit() {
        bike1a.reserve(overlappingDateRange);
        bike2a.reserve(overlappingDateRange);
        Collection<Quote> quotes = sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7"));
        Iterator<Quote> quotesIterator = quotes.iterator();
        Quote quote = quotesIterator.next(); //there should be exactly one offer 
        // deposit should be equal to (2*1500+300)*0.12=396
        assertEquals(quote.getDeposit().getValue().compareTo(new BigDecimal(396)), 0);
    }
    
    @Test
    void getQuotetestPrice() {
        bike1a.reserve(overlappingDateRange);
        bike2a.reserve(overlappingDateRange);
        Collection<Quote> quotes = sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7"));
        Iterator<Quote> quotesIterator = quotes.iterator();
        Quote quote = quotesIterator.next(); //there should be exactly one offer
        // price should (2*10+20)*5=200
        assertEquals(quote.getTotalPrice().compareTo(new BigDecimal(200)), 0);
    }
    
    @Test
    void getQuotetestCustomPricingPolicy() {
        MultidayDiscountsPolicy pricingPolicy = new MultidayDiscountsPolicy();
        pricingPolicy.setDiscount(2, "0.05");
        provider1.setCustomPricingPolicy(pricingPolicy);
        bike1a.reserve(overlappingDateRange);
        bike2a.reserve(overlappingDateRange);
        Collection<Quote> quotes = sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7"));
        Iterator<Quote> quotesIterator = quotes.iterator();
        Quote quote = quotesIterator.next(); //there should be exactly one offer
        // price should be 200*0.95=190
        assertEquals(quote.getTotalPrice().compareTo(new BigDecimal(190)), 0);
    }
    
    @Test
    void bookQuotetest1() {
        bike1a.reserve(overlappingDateRange);
        bike2a.reserve(overlappingDateRange);
        Collection<Quote> quotes = sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7"));
        Iterator<Quote> quotesIterator = quotes.iterator();
        Quote quote = quotesIterator.next(); //there should be exactly one offer
        Booking booking = sys.bookQuote(quote, new Customer("Tom", "Tomson", "Clerk Stret 7", "EH4 789", "079456412",
                "email@abc.co.uk", "password"), true);
        booking.sendConfirmation();
        assertFalse(booking.getDeposit().isPaid()); // test if deposit is unpaid on default
        booking.depositPaid();                      // provider gives bikes to customer and updates booking
        assertTrue(booking.getDeposit().isPaid()); // test if deposit was updated
        Collection<Bike> bikes = quote.getBikes();
        for(Bike bike : bikes) {
            assertFalse(bike.isAvailable(overlappingDateRange));
        }
        // first bike provider still have enough bikes to offer the quote
        assertEquals(sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7")).size(), 1);
    }
    
    @Test
    void bookQuotetest2() {
        bike1a.reserve(overlappingDateRange);
        bike2a.reserve(overlappingDateRange);
        bike1.reserve(overlappingDateRange);
        bike2.reserve(overlappingDateRange);
        Collection<Quote> quotes = sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7"));
        Iterator<Quote> quotesIterator = quotes.iterator();
        Quote quote = quotesIterator.next(); //there should be exactly one offer
        sys.bookQuote(quote, new Customer("Tom", "Bikeworld", "Clerk Stret 7", "EH4 789", "079456412",
                "email@abc.co.uk", "password"), true);
        Collection<Bike> bikes = quote.getBikes();
        for(Bike bike : bikes) {
            assertFalse(bike.isAvailable(overlappingDateRange));
        }
        // now bike provider one has no bikes to offer
        assertEquals(sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7")).size(), 0);
    }
    
    @Test
    void bookQuotetesWithDelivery() {
        bike1a.reserve(overlappingDateRange);
        bike2a.reserve(overlappingDateRange);
        bike1.reserve(overlappingDateRange);
        bike2.reserve(overlappingDateRange);
        Collection<Quote> quotes = sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7"));
        Iterator<Quote> quotesIterator = quotes.iterator();
        Quote quote = quotesIterator.next(); //there should be exactly one offer
        Booking booking = sys.bookQuote(quote, new Customer("Tom", "Bikeworld", "Clerk Stret 7", "EH4 789", "079456412",
                "email@abc.co.uk", "password"), false);
        Collection<Bike> bikes = quote.getBikes();
        ((MockDeliveryService)DeliveryServiceFactory.getDeliveryService()).carryOutPickups(date1);
        for(Bike bike : bikes) {
            assertFalse(bike.isAvailable(overlappingDateRange));
            assertTrue(bike.isBeingDelivered()); // test if delivery service affects state of the bike
        }
        
        ((MockDeliveryService)DeliveryServiceFactory.getDeliveryService()).carryOutPickups(date1);
        for(Bike bike : bikes) {
            assertFalse(bike.isAvailable(overlappingDateRange));
            assertTrue(bike.isBeingDelivered()); // test if delivery service affects state of the bike
            assertTrue(booking.getDeposit().isBeingDelivered());
        }
        
        ((MockDeliveryService)DeliveryServiceFactory.getDeliveryService()).carryOutDropoffs();
        for(Bike bike : bikes) {
            assertFalse(bike.isAvailable(overlappingDateRange));
            assertFalse(bike.isBeingDelivered()); // test if delivery service affects state of the bike
            assertTrue(bike.isDeliveryComplete()); // test if delivery service affects state of the bike
            assertTrue(booking.getDeposit().isPaid()); // test if delivery updated the status of deposit
        }   
    }
    
    @Test
    void returnBikesTest() {
        bike1a.reserve(overlappingDateRange);
        bike2a.reserve(overlappingDateRange);
        Collection<Quote> quotes = sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7"));
        Iterator<Quote> quotesIterator = quotes.iterator();
        Quote quote = quotesIterator.next(); //there should be exactly one offer
        Booking booking = sys.bookQuote(quote, new Customer("Tom", "Tomson", "Clerk Stret 7", "EH4 789", "079456412",
                "email@abc.co.uk", "password"), true);
        booking.sendConfirmation();
        booking.depositPaid();                      // provider gives bikes to customer and updates booking
        booking.bikesGivenToCustomer();  
        Collection<Bike> bikes = quote.getBikes();

        // Customer returns bikes
        booking.registerDepositReturn(); 
        for(Bike bike : bikes) {
            assertFalse(bike.isInStore());
            provider1.registerBikeReturn(bike.getBikeId());
            assertTrue(bike.isInStore());
        }
        provider1.makeBookingCompleted(booking.getBookingId());
        assertTrue(booking.isCompleted());        
    }
    
    @Test
    void returnBikesToParnterTest() {
        provider2.addPartner(provider1); // Establish partnership
        provider1.addPartner(provider2);
        bike1a.reserve(overlappingDateRange);
        bike2a.reserve(overlappingDateRange);
        Collection<Quote> quotes = sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7"));
        Iterator<Quote> quotesIterator = quotes.iterator();
        Quote quote = quotesIterator.next(); //there should be exactly one offer
        Booking booking = sys.bookQuote(quote, new Customer("Tom", "Tomson", "Clerk Stret 7", "EH4 789", "079456412",
                "email@abc.co.uk", "password"), true);
        booking.sendConfirmation();
        booking.depositPaid();                      // provider gives bikes to customer and updates booking
        booking.bikesGivenToCustomer();  
        Collection<Bike> bikes = quote.getBikes();
        // Customer returns bikes to partner
        for(Bike bike : bikes) {
            assertFalse(bike.isInStore()); // Test delivery handling
            provider2.registerBikeReturnToPartner(provider1, bike.getBikeId());
            assertFalse(bike.isBeingDelivered());
            ((MockDeliveryService)DeliveryServiceFactory.getDeliveryService()).carryOutPickups(LocalDate.now());
            assertTrue(bike.isBeingDelivered());
            ((MockDeliveryService)DeliveryServiceFactory.getDeliveryService()).carryOutDropoffs();
            assertFalse(bike.isBeingDelivered());
            assertTrue(bike.isInStore());
        }
        //Partner returns deposit
        provider2.registerDepositReturnToPartner(provider1, booking.getBookingId());
        assertTrue(booking.getDeposit().isReturned());
        provider1.makeBookingCompleted(booking.getBookingId()); // Provider one finalize booking
        assertTrue(booking.isCompleted());             
    }
    
    @Test
    void testMockValuationPolicy() {
        ValuationPolicyFactory.setupMockValuationPolicyService();
        
        provider1.setCustomValuationPolicy(ValuationPolicyFactory.getValuationPolicy());
        bike1a.reserve(overlappingDateRange);
        bike2a.reserve(overlappingDateRange);
        Collection<Quote> quotes = sys.getQuotes(bikeMap1, dateRange, new Location("EH4 789", "Clerk Stret 7"));
        Iterator<Quote> quotesIterator = quotes.iterator();
        Quote quote = quotesIterator.next(); //there should be exactly one offer 
        // mock valuation policy always return one so for three bikes
        // and deposit rate 0.12 it should be equal to 0.36
        assertEquals(quote.getDeposit().getValue().compareTo(new BigDecimal("0.36")), 0);
    }
    
}
