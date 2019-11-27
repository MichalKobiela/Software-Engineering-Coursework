package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;

public class MockMultidayDiscountsPolicy implements PricingPolicy {

    @Override
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
        // TODO Auto-generated method stub
        return null;
    }

}
