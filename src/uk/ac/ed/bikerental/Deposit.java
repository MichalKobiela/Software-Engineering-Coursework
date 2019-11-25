package uk.ac.ed.bikerental;

import java.math.BigDecimal;

public class Deposit implements Deliverable {
    private BigDecimal value;
    private boolean isBeingDelivered;
    private boolean paid;
    private boolean returned;

    public BigDecimal getValue() {
        return value;
    }

    public boolean isBeingDelivered() {
        return isBeingDelivered;
    }

    public boolean isPaid() {
        return paid;
    }

    public boolean isReturned() {
        return returned;
    }

    public Deposit(BigDecimal value, boolean depositPaid, boolean depositReturned) {
        super();
        this.value = value;
        this.isBeingDelivered = false;
        this.paid = depositPaid;
        this.returned = depositReturned;
    }
    // When bike provider use delivery system to deliver deposit to customer it means that delivery will collect deposit from customer
    // and then it is assumed that delivery will tranfer money to bike provider outside the system.
    @Override
    public void onPickup() {
        isBeingDelivered = true;

    }

    @Override
    public void onDropoff() {
        isBeingDelivered = false;
        paid=true;

    }

    public void depositReturn() {
        returned = true;
        
    }
    
    public void depositPaid() {
        paid = true;
        
    }

}