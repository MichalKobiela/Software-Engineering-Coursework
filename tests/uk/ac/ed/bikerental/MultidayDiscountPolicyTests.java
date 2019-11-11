package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.*;


public class MultidayDiscountPolicyTests {

    private BigDecimal high, medium, low;
    private BikeType type1, type2, type3;
    private Bike bike1, bike2, bike3, bike4, bike5;
    private MultidayDiscountsPolicy policy1, policy2;
    private Collection<Bike> bikes;
    
    @BeforeEach
    void setUp() throws Exception {
        this.high = new BigDecimal(1500);
        this.medium = new BigDecimal(300);
        this.low = new BigDecimal("50.12");
        
        type1 = new BikeType("type1",high);
        type2 = new BikeType("type1",medium);
        type3 = new BikeType("type1",low);
        bike1 = new Bike(1,type1);
        bike2 = new Bike(2,type1);
        bike3 = new Bike(3,type2);
        bike4 = new Bike(1,type1);
        bike5 = new Bike(1,type3);
        bikes = new HashSet<Bike>();
        bikes.add(bike1);
        bikes.add(bike2);
        bikes.add(bike3);
        bikes.add(bike4);
        bikes.add(bike5);

        /* policy1:
         * 0-2 days 0%
         * 3-4 days 5%
         * 5+  days 10%
         * 
         */

        policy1 = new MultidayDiscountsPolicy();
        policy1.setDiscount(3, "0.05");
        policy1.setDiscount(5, "0.1");
        policy1.setDailyRentalPrice(type1, high);
        policy1.setDailyRentalPrice(type2, medium);
        policy1.setDailyRentalPrice(type3, low);
        
        /* policy2:
         * 0-2 days 0%
         * 3-4 days 5%
         * 5-10  days 2%
         * 11+  days 11%
         */
        
        policy2 = new MultidayDiscountsPolicy();
        policy2.setDiscount(3, "0.05");
        policy2.setDiscount(5, "0.02");
        policy2.setDiscount(10, "0.11");
        policy2.setDailyRentalPrice(type1, high);
        policy2.setDailyRentalPrice(type2, medium);
        policy2.setDailyRentalPrice(type3, low);
    }
    
    //policy1
    @Test
    void test1() {
        // 4 days
        LocalDate date1 = LocalDate.of(2019, 11, 1);
        LocalDate date2 = LocalDate.of(2019, 11, 5);
        DateRange dateRange = new DateRange(date1, date2);
        // should be (3*1500+300+50.12)*0.95 = 4607.614
        assertEquals(policy1.calculatePrice(bikes, dateRange).compareTo(new BigDecimal("4607.614")),0);
    }
    
    @Test
    void test2() {
        // 1 day
        LocalDate date1 = LocalDate.of(2019, 11, 1);
        LocalDate date2 = LocalDate.of(2019, 11, 2);
        DateRange dateRange = new DateRange(date1, date2);
        // should be (3*1500+300+50.12) = 4850.12
        assertEquals(policy1.calculatePrice(bikes, dateRange).compareTo(new BigDecimal("4850.12")),0);
    }

    @Test
    void test3() {
        // 5 days
        LocalDate date1 = LocalDate.of(2019, 11, 1);
        LocalDate date2 = LocalDate.of(2019, 11, 6);
        DateRange dateRange = new DateRange(date1, date2);
        // should be (3*1500+300+50.12)*0.9 = 4365.108
        assertEquals(policy1.calculatePrice(bikes, dateRange).compareTo(new BigDecimal("4365.108")),0);
    }
    
    @Test
    void test4() {
        // 6 days
        LocalDate date1 = LocalDate.of(2019, 11, 1);
        LocalDate date2 = LocalDate.of(2019, 11, 7);
        DateRange dateRange = new DateRange(date1, date2);
        // should be (3*1500+300+50.12)*0.9 = 4365.108
        assertEquals(policy1.calculatePrice(bikes, dateRange).compareTo(new BigDecimal("4365.108")),0);
    }
    @Test
    void test5() {
        // 6 days after reseting discounts
        LocalDate date1 = LocalDate.of(2019, 11, 1);
        LocalDate date2 = LocalDate.of(2019, 11, 7);
        DateRange dateRange = new DateRange(date1, date2);
        policy1.resetDiscounts();
        // should be (3*1500+300+50.12)*1 = 4850.12
        assertEquals(policy1.calculatePrice(bikes, dateRange).compareTo(new BigDecimal("4850.12")),0);
    }
    @Test
    void test6() {
        // 11 days with policy2
        LocalDate date1 = LocalDate.of(2019, 11, 1);
        LocalDate date2 = LocalDate.of(2019, 11, 11);
        DateRange dateRange = new DateRange(date1, date2);
        // should be (3*1500+300+50.12)*0.89 = 4316.6068
        assertEquals(policy2.calculatePrice(bikes, dateRange).compareTo(new BigDecimal("4316.6068")),0);
    }
    @Test
    void test7() {
        // 9 days with policy2
        LocalDate date1 = LocalDate.of(2019, 11, 2);
        LocalDate date2 = LocalDate.of(2019, 11, 11);
        DateRange dateRange = new DateRange(date1, date2);
        // should be (3*1500+300+50.12)*0.98 = 4753.1176
        assertEquals(policy2.calculatePrice(bikes, dateRange).compareTo(new BigDecimal("4753.1176")),0);
    }
    
}