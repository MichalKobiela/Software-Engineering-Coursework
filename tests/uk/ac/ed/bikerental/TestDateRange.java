package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestDateRange {
    private DateRange dateRange1, dateRange2, dateRange3, dateRange4, dateRange5;

    @BeforeEach
    void setUp() throws Exception {
        // Setup resources before each test
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 5), LocalDate.of(2019, 1, 23));
        this.dateRange3 = new DateRange(LocalDate.of(2015, 1, 7), LocalDate.of(2018, 1, 10));
        this.dateRange4 = new DateRange(LocalDate.of(1753, 5, 5), LocalDate.of(3000, 5, 7));
        this.dateRange5 = new DateRange(LocalDate.of(3001, 1, 1), LocalDate.of(3001, 2, 1));

    }

    // Sample JUnit tests checking toYears works
    @Test
    void testToYears1() {
        assertEquals(0, this.dateRange1.toYears());
    }

    @Test
    void testToYears3() {
        assertEquals(3, this.dateRange3.toYears());
    }

    @Test
    void testOverlapsTrue() {
        // TODO: check we can see when two date ranges overlap
        assertEquals(true, this.dateRange1.overlaps(dateRange2));
        assertEquals(true, this.dateRange2.overlaps(dateRange1));
        assertEquals(true, this.dateRange1.overlaps(dateRange4));
        assertEquals(true, this.dateRange2.overlaps(dateRange4));
        assertEquals(true, this.dateRange3.overlaps(dateRange4));
        assertEquals(true, this.dateRange4.overlaps(dateRange1));
        assertEquals(true, this.dateRange4.overlaps(dateRange2));
        assertEquals(true, this.dateRange4.overlaps(dateRange3));
    }

    @Test
    void testOverlapsFalse() {
        assertEquals(false, this.dateRange1.overlaps(dateRange3));
        assertEquals(false, this.dateRange3.overlaps(dateRange1));
        assertEquals(false, this.dateRange2.overlaps(dateRange3));
        assertEquals(false, this.dateRange3.overlaps(dateRange2));
        assertEquals(false, this.dateRange4.overlaps(dateRange5));

        assertEquals(false, this.dateRange1.overlaps(dateRange5));
        assertEquals(false, this.dateRange2.overlaps(dateRange5));
        assertEquals(false, this.dateRange3.overlaps(dateRange5));
        assertEquals(false, this.dateRange4.overlaps(dateRange5));

        assertEquals(false, this.dateRange5.overlaps(dateRange1));
        assertEquals(false, this.dateRange5.overlaps(dateRange2));
        assertEquals(false, this.dateRange5.overlaps(dateRange3));
        assertEquals(false, this.dateRange5.overlaps(dateRange4));

    }

    // TODO: put some of your own unit tests here

    @Test
    void testToYears4() {
        assertEquals(1247, this.dateRange4.toYears());
    }

    @Test
    void testToDays() {
        assertEquals(3, this.dateRange1.toDays());
        assertEquals(18, this.dateRange2.toDays());
        assertEquals(1099, this.dateRange3.toDays());
        assertEquals(455459, this.dateRange4.toDays());
        //
        // Note: I used an online date range calculator for the longer days
    }

    //@Test
    //void testConstructor() { assertThrows(, executable) }
    

}
