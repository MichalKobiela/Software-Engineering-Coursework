package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.Collection;

public class Bike implements Deliverable {
    private long bikeId;
    private BikeType type;
    private Collection<DateRange> datesReserved;
    private boolean isInStore;
    private boolean isBeingDelivered;
    
    public Bike(long bikeId, BikeType type) {
        this.bikeId = bikeId;
        this.type = type;
        this.isInStore = true;
        this.datesReserved = new ArrayList<DateRange>();
        this.isBeingDelivered = false;
    }
    public BikeType getType() {
        return type;
    }
    public boolean isAvailable(DateRange reserveRange) {
        for(DateRange dateRange : datesReserved) {
            if(dateRange.overlaps(reserveRange)) {
                return false;
            }
        }
        return true;
    }
    public boolean reserve(DateRange dateRange) {
        if(this.isAvailable(dateRange)) {
            datesReserved.add(dateRange);
            return true;
        }
        else {
            return false;
        }
    }
    public Collection<DateRange> getDatesReserved() {
        return datesReserved;
    }
    public void setDatesReserved(Collection<DateRange> datesReserved) {
        this.datesReserved = datesReserved;
    }
    public long getBikeId() {
        return bikeId;
    }
    public void setBikeId(long bikeId) {
        this.bikeId = bikeId;
    }
    public boolean isInStore() {
        return isInStore;
    }
    public void setInStore(boolean isInStore) {
        this.isInStore = isInStore;
    }
    public void setType(BikeType type) {
        this.type = type;
    }
    
    @Override
    public void onPickup() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void onDropoff() {
        // TODO Auto-generated method stub
        
    }
    
}