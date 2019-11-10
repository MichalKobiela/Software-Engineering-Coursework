package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

public class BikeType {
    private String name;
    private BigDecimal replecementValue;
    
    
    public BikeType(String name, BigDecimal replecementValue) {
        assert !name.isEmpty();
        this.name = name;
        this.replecementValue = replecementValue;
    }

    public BigDecimal getReplacementValue() {
        return replecementValue;
        //assert false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReplecementValue(BigDecimal replecementValue) {
        this.replecementValue = replecementValue;
    }
    
}