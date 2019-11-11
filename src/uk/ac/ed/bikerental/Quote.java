package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;

public class Quote {
    private BikeProvider bikeProvider;
    private DateRange dateRange;
    private BigDecimal totalPrice;
    private BigDecimal deposit;
    private Collection<Bike> bikes;
    
    public Quote(BikeProvider bikeProvider, DateRange dateRange, BigDecimal totalPrice, BigDecimal deposit,
            Collection<Bike> bikes) {
        super();
        this.bikeProvider = bikeProvider;
        this.dateRange = dateRange;
        this.totalPrice = totalPrice;
        this.deposit = deposit;
        this.bikes = bikes;
    }

    public boolean reserveBikes() {
        if (isEmpty()) {  // Why we want it to be empty?
            return false;
        }
        else {
            for(Bike bike: bikes) {
                bike.reserve(dateRange);
           }
           return true;
        }

    }
    
    public boolean isEmpty() {
        return bikes.isEmpty();
    }
    
    public void registerReturnOfBikes() {
        for (Bike bike: bikes) {
            bike.setInStore(true);
        }
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public Collection<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(Collection<Bike> bikes) {
        this.bikes = bikes;
    }
    
    
    
}
