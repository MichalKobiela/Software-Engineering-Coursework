package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class StandardPricingPolicy implements PricingPolicy {


    private Map<BikeType, BigDecimal> prices;

    @Override
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
        prices.put(bikeType, dailyPrice);
        
    }

    @Override
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
        BigDecimal price = new BigDecimal(0);
        long numOfDays = duration.toDays();
        for(Bike bike : bikes) {
            BigDecimal priceOfBike = prices.get(bike.getType());
            price = price.add(priceOfBike);
        }
        price = price.multiply(new BigDecimal(numOfDays));
        return price;
    }
    
    
    public StandardPricingPolicy(Map<BikeType, BigDecimal> prices) {
        this.prices = prices;
    }
    
    public StandardPricingPolicy() {
        this.prices = new HashMap<BikeType, BigDecimal>();
    }
}
