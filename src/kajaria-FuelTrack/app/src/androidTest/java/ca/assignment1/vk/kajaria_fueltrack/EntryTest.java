package ca.assignment1.vk.kajaria_fueltrack;

import android.test.ActivityInstrumentationTestCase2;


import java.util.Date;

/**
 * Created by VK on 28/01/2016.
 */
public class EntryTest extends ActivityInstrumentationTestCase2{
    public EntryTest(){
        super(FuelTrackActivity.class);
    }

    public void testGetDate(){
        Date testDate = new Date();
        Entry entry = new Entry(testDate,"test", 0.00, "test", 0.000, 0.0);
        assertEquals(testDate, entry.getDate());
    }

    public void testGetStation(){
        Entry entry = new Entry(new Date(),"test", 0.00, "test", 0.000, 0.0);
        assertEquals("test", entry.getStation());
    }

    public void testGetOdometer(){
        Entry entry = new Entry(new Date(),"test", 0.00, "test", 0.000, 0.0);
        assertEquals(0.00, entry.getOdometer());
    }

    public void testGetGrade() {
        Entry entry = new Entry(new Date(), "test", 0.00, "test", 0.000, 0.0);
        assertEquals("test", entry.getGrade());
    }

    public void testGetAmount() {
        Entry entry = new Entry(new Date(), "test", 0.00, "test", 0.000, 0.0);
        assertEquals(0.000, entry.getAmount());
    }

    public void testGetUnitCost() {
        Entry entry = new Entry(new Date(), "test", 0.00, "test", 0.000, 0.0);
        assertEquals(0.0, entry.getUnitcost());
    }
}
