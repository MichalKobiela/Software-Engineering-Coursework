package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * Class implements PricingPolicy allowing bike providers
 * to offer discounts based on length of the booking
 * 
 * Assumption: The discounts are getting bigger for longer durations
 * 
 * @author s1870794
 *
 */
public class MultidayDiscountsPolicy implements PricingPolicy {

    private Map<BikeType, BigDecimal> prices;
    private Map<Integer, Double> discounts;

    @Override
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
        prices.put(bikeType, dailyPrice);
        
    }

    @Override
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
        BigDecimal price= new BigDecimal(0);
        long numOfDays = duration.toDays();
        double percentage= chooseDiscount(numOfDays);
        for(Bike bike : bikes) {
            BigDecimal priceOfBike = prices.get(bike.getType());
            price.add(priceOfBike);
        }
        return price.multiply(new BigDecimal(1-percentage));
    }
    
    
    /**
     * Allows user to specify/update their pricing policy 
     *
     * @param  minNumOfDays  minimum duration to qualify for a discount 
     * @param  percentage the value of discount e.g. for 5% off use 0.05
     * 
     * 
     * 
     */
    public void setDiscount(Integer minNumOfDays, double percentage) {
        discounts.put(minNumOfDays, percentage);
    }
    
    
    /**
     * Calculates discount amount for given duration 
     *
     * @param  numOfDays length in days of hire
     * 
     */
    public double chooseDiscount(long numOfDays) {
        double result = 0;
        for(Integer days: discounts.keySet()) {
            if(numOfDays>=days) {
                if(discounts.get(days) > result) {
                    result = discounts.get(days);                    
                }
            }
        }
        return result;
    }
}
