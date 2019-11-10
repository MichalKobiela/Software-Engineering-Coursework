package uk.ac.ed.bikerental;

import java.util.Collection;

public class Quote {
    private BikeProvider bikeProvider;
    private DateRange dateRange;
    private double totalPrice;
    private double deposit;
    private Collection<Bike> bikes;
    
    public Quote(BikeProvider bikeProvider, DateRange dateRange, double totalPrice, double deposit,
            Collection<Bike> bikes) {
        super();
        this.bikeProvider = bikeProvider;
        this.dateRange = dateRange;
        this.totalPrice = totalPrice;
        this.deposit = deposit;
        this.bikes = bikes;
    }

    public boolean reserveBikes() {
        if (isEmpty()) {
           for(Bike bike: bikes) {
                bike.reserve(dateRange);
           }
           return true;
        }
        else {
            return false;
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
    
}
