package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.Collection;

public class Bike {
    private long bikeId;
    private BikeType type;
    private Collection<DateRange> datesReserved;
    private boolean isInStore;
    
    public Bike(long bikeId, BikeType type) {
        this.bikeId = bikeId;
        this.type = type;
        this.isInStore = true;
        this.datesReserved = new ArrayList<DateRange>();
    }
    public BikeType getType() {
        //assert false;
        return type;
    }
    public boolean isAvailable(DateRange reserveRange) {
        for(DateRange dateRange :datesReserved) {
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
    
}