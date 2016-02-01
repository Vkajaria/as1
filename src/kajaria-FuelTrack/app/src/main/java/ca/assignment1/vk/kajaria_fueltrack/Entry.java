package ca.assignment1.vk.kajaria_fueltrack;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VK on 27/01/2016.
 */
public class Entry {
    private Date date;
    private String station;
    private double odometer;
    private String grade;
    private double amount;
    private double unitcost;
    private double totalcost;

    public Entry(Date date, String station, double odometer, String grade, double amount, double unitcost) {
        this.totalcost = round(unitcost * amount/100,2);
        this.date = date;
        this.station = station;
        this.odometer = round(odometer,1);
        this.grade = grade;
        this.amount = round(amount,3);
        this.unitcost = round(unitcost,1);
    }

    private double round(double value, int dp){
        long scale = (long) Math.pow(10, dp);
        value = value * scale;
        long temp = Math.round(value);
        return (double) temp / scale;
    }

    public Date getDate() {
        return date;
    }

    public String getStation() {
        return station;
    }

    public double getOdometer() {
        return odometer;
    }

    public double getAmount() {
        return amount;
    }

    public double getUnitcost() {
        return unitcost;
    }

    public String getGrade() {
        return grade;
    }

    public double getTotalcost() {
        return totalcost;
    }


    @Override
    public String toString() {
        return "Date: " + new SimpleDateFormat("yyyy-MM-dd").format(date) + "\n"
                + "Station: " + station + "\n"
                + "Odometer: " + odometer + "km\n"
                + "Fuel Grade: " + grade + "\n"
                + "Amount: " + amount + "L\n"
                + "Unit Cost: " + unitcost + " cents/L\n"
                + "Fuel Cost: $" + totalcost;
    }
}
