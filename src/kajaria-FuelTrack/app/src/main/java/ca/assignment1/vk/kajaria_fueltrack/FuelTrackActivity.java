//Main activity class
//Landing page

package ca.assignment1.vk.kajaria_fueltrack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static ca.assignment1.vk.kajaria_fueltrack.R.layout.list_item;

public class FuelTrackActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ListView logEntriesList;

    private ArrayList<Entry> entries = new ArrayList<Entry>();
    private ArrayAdapter<Entry> adapter;

    private String type_key = "type_key";
    private String date_key = "date_key";
    private String station_key = "station_key";
    private String odometer_key = "odometer_key";
    private String grade_key = "grade_key";
    private String unit_key = "unit_key";
    private String amount_key = "amount_key";
    private String index_key = "index_key";
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_track);

        logEntriesList = (ListView) findViewById(R.id.logEntriesList);

        Button newEntry = (Button) findViewById(R.id.newEntry);
        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;
                Intent myIntent = new Intent(FuelTrackActivity.this, EntryActivity.class);
                startActivityForResult(myIntent, 1);
            }
        });

        ListView list = (ListView) findViewById(R.id.logEntriesList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View View, int Pos, long id) {
                Entry entry = entries.get(Pos);
                Date date = entry.getDate();
                String temp = new SimpleDateFormat("yyyy-mm-dd").format(date);
                String station = entry.getStation();
                double odometer = entry.getOdometer();
                String grade = entry.getGrade();
                double amount = entry.getAmount();
                double unit = entry.getUnitcost();

                Intent intent = new Intent(FuelTrackActivity.this, EntryActivity.class);
                type = 1;
                intent.putExtra(type_key, 1);
                intent.putExtra(index_key, Pos);
                intent.putExtra(date_key, temp);
                intent.putExtra(station_key, station);
                intent.putExtra(odometer_key, odometer);
                intent.putExtra(grade_key, grade);
                intent.putExtra(unit_key, unit);
                intent.putExtra(amount_key, amount);

                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Entry>(this,
                R.layout.list_item, entries);
        logEntriesList.setAdapter(adapter);
        updateTotalCost();
    }

    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html - Jan 21st 2016
            Type listType = new TypeToken<ArrayList<Entry>>() {}.getType();
            entries = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            entries = new ArrayList<Entry>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(entries, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    //On return from editing/creating an activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){


                String temp = data.getStringExtra(date_key);
                DateFormat DF = new SimpleDateFormat("yyyy-mm-dd");
                Date date;
                try {
                    date = DF.parse(temp);
                } catch (Exception e) {
                    date = new Date(System.currentTimeMillis());
                }
                String station = data.getStringExtra(station_key);
                double odometer = data.getDoubleExtra(odometer_key, 0);
                String grade = data.getStringExtra(grade_key);
                double unit = data.getDoubleExtra(unit_key, 0);
                double amount = data.getDoubleExtra(amount_key,0);
                Entry newest = new Entry(date, station, odometer, grade, amount, unit);

                if(type == 0) {
                    //add
                    entries.add(newest);
                    adapter.notifyDataSetChanged();
                    updateTotalCost();
                    saveInFile();

                } else{
                    //edit
                    int index = data.getIntExtra(index_key,0);
                    entries.set( index, newest);
                    adapter.notifyDataSetChanged();
                    updateTotalCost();
                    saveInFile();


                }
            }
        }
    }

    //updates Total cost
    private void updateTotalCost(){

        int n = entries.size();
        double total = 0.00;
        for( int i = 0; i <= n-1; ++i ){
            Entry temp = entries.get(i);
            total = total + temp.getTotalcost();
        }

        TextView totalcost = (TextView) findViewById(R.id.overallFuelCost);
        totalcost.setText("Total Cost: $" + total);

    }

}
