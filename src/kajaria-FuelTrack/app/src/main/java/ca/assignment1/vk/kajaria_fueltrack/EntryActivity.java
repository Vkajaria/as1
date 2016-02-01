//Activity for editing/creating entries

package ca.assignment1.vk.kajaria_fueltrack;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryActivity extends AppCompatActivity {

    String temp;
    Date date;
    String station;
    double odometer;
    String grade;
    double unitcost;
    double amount;

    private String type_key = "type_key";
    private String date_key = "date_key";
    private String station_key = "station_key";
    private String odometer_key = "odometer_key";
    private String grade_key = "grade_key";
    private String unit_key = "unit_key";
    private String amount_key = "amount_key";
    private String index_key = "index_key";

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        //fills fields if editing an entry, does nothing otherwise
        fillAllFields();

        Button cancelEntry = (Button) findViewById(R.id.cancel);
        cancelEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button saveEntry = (Button) findViewById(R.id.save);
        saveEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllFields();
            }
            });
    }

    //checks if all fields have been sufficiently filled in and then returns data to FuelTrackActivity
    public void checkAllFields(){

        //get date
        EditText editdate = (EditText) findViewById(R.id.entryDate);
        temp = editdate.getText().toString();
        DateFormat DF = new SimpleDateFormat("yyyy-mm-dd");
        try{
            date = DF.parse(temp);
        }catch(Exception e){
            editdate.setError("Please fill in this field properly");
            return;
        }

        //get station
        EditText editstat = (EditText) findViewById(R.id.entryStation);
        station = editstat.getText().toString();
        if(station.trim().equals("")){
            editstat.setError("Please fill in this field properly");
            return;
        }

        //get odometer
        EditText editodom = (EditText) findViewById(R.id.entryOdomoter);
        try{
            odometer = Double.parseDouble(editodom.getText().toString());
        }catch(Exception e){
            editodom.setError("Please fill in this field properly");
            return;
        }

        //get grade
        EditText editgrade = (EditText) findViewById(R.id.entryGrade);
        grade = editgrade.getText().toString();
        if(grade.trim().equals("")){
            editgrade.setError("Please fill in this field properly");
            return;
        }

        //get amount
        EditText editamount = (EditText) findViewById(R.id.entryAmount);
        try{
            amount = Double.parseDouble(editamount.getText().toString());
        }catch(Exception e){
            editamount.setError("Please fill in this field properly");
            return;
        }

        //get unit cost
        EditText editcost = (EditText) findViewById(R.id.entryUnitCost);
        try{
            unitcost = Double.parseDouble(editcost.getText().toString());
        }catch(Exception e){
            editcost.setError("Please fill in this field properly");
            return;

        }

        Intent returnIntent = new Intent();
        // send data back
        returnIntent.putExtra(index_key, index);
        returnIntent.putExtra( date_key, temp);
        returnIntent.putExtra( station_key, station);
        returnIntent.putExtra(odometer_key, odometer);
        returnIntent.putExtra(grade_key, grade);
        returnIntent.putExtra(unit_key, unitcost);
        returnIntent.putExtra(amount_key, amount);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();


    }

    //fills up field with old data if editing an entry
    public void fillAllFields(){
        Intent data = getIntent();
        int type = data.getIntExtra(type_key, 0);

        if (type != 1){
            return;
        }

        String date = data.getStringExtra(date_key);
        String station = data.getStringExtra(station_key);
        double odometer = data.getDoubleExtra(odometer_key, 0);
        String grade = data.getStringExtra(grade_key);
        double unit = data.getDoubleExtra(unit_key, 0);
        double amount = data.getDoubleExtra(amount_key, 0);
        index = data.getIntExtra(index_key, 0);

        EditText edit;
        //date
        edit = (EditText) findViewById(R.id.entryDate);
        edit.setText(date, TextView.BufferType.EDITABLE);
        //station
        edit = (EditText) findViewById(R.id.entryStation);
        edit.setText(station, TextView.BufferType.EDITABLE);
        //odometer
        edit = (EditText) findViewById(R.id.entryOdomoter);
        edit.setText(String.valueOf(odometer), TextView.BufferType.EDITABLE);
        //grade
        edit = (EditText) findViewById(R.id.entryGrade);
        edit.setText(grade, TextView.BufferType.EDITABLE);
        //amount
        edit = (EditText) findViewById(R.id.entryAmount);
        edit.setText(String.valueOf(amount), TextView.BufferType.EDITABLE);
        //unit price
        edit = (EditText) findViewById(R.id.entryUnitCost);
        edit.setText(String.valueOf(unit), TextView.BufferType.EDITABLE);
    }
}
