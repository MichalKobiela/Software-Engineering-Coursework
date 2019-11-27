package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;

public class Quote {
    private BikeProvider bikeProvider;
    private DateRange dateRange;
    private BigDecimal totalPrice;
    private Deposit deposit;
    private Collection<Bike> bikes;
    
    public Quote(BikeProvider bikeProvider, DateRange dateRange, BigDecimal totalPrice, BigDecimal deposit,
            Collection<Bike> bikes) {
        this.bikeProvider = bikeProvider;
        this.dateRange = dateRange;
        this.totalPrice = totalPrice;
        this.deposit = new Deposit(deposit, false, false);
        this.bikes = bikes;
    }

    public boolean reserveBikes() {
        if (isEmpty()) { 
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
    
    public BikeProvider getBikeProvider() {
        return bikeProvider;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bikeProvider == null) ? 0 : bikeProvider.hashCode());
        result = prime * result + ((bikes == null) ? 0 : bikes.hashCode());
        result = prime * result + ((dateRange == null) ? 0 : dateRange.hashCode());
        result = prime * result + ((deposit == null) ? 0 : deposit.hashCode());
        result = prime * result + ((totalPrice == null) ? 0 : totalPrice.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Quote other = (Quote) obj;
        if (bikeProvider == null) {
            if (other.bikeProvider != null)
                return false;
        } else if (!(bikeProvider==other.bikeProvider))
            return false;
        if (bikes == null) {
            if (other.bikes != null)
                return false;
        } else if (!bikes.equals(other.bikes))
            return false;
        if (dateRange == null) {
            if (other.dateRange != null)
                return false;
        } else if (!dateRange.equals(other.dateRange))
            return false;
        if (deposit == null) {
            if (other.deposit != null)
                return false;
        } else if (!deposit.equals(other.deposit))
            return false;
        if (totalPrice == null) {
            if (other.totalPrice != null)
                return false;
        } else if (!totalPrice.equals(other.totalPrice))
            return false;
        return true;
    }

    public Deposit getDeposit() {
        return deposit;
    }
    
    
    
}
