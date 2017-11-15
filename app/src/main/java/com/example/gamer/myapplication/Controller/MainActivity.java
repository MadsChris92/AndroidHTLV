package com.example.gamer.myapplication.Controller;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamer.myapplication.R;
import com.example.gamer.myapplication.Utility.MyAlarmReceiver;
import com.example.gamer.myapplication.Utility.WebSearch;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener{
    /**
     * Der skal bruges internet forbindelse ellers vil  appen crashe ved startup
     */

    public static WebSearch webSearch;
    private EditText nameTxt;
    private String username;
    private TextView locTxt;
    LocationManager locationManager;
    private TextView welTxt;
    MyAlarmReceiver myAlarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = "Mads";

        nameTxt = (EditText) findViewById(R.id.nameEditText);

        webSearch = new WebSearch();
        webSearch.execute();

        locTxt = (TextView) findViewById(R.id.locationTxt);
        locTxt.setText("Searching for GPS...");
        welTxt = (TextView) findViewById(R.id.welcomeTxt);


        locationSetup();
        myAlarmReceiver = new MyAlarmReceiver();
        Button b = (Button) findViewById(R.id.button);




        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmManager manger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent myIntent;
                PendingIntent pendingIntent;

                myIntent = new Intent(MainActivity.this, MyAlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0,myIntent, 0);

                manger.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 3000, pendingIntent);
            }
        });


    }

    public void locationSetup(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }



    public void changeScene(Class c, String extraKey, String extraValue) {

        if(!username.isEmpty()){
            Intent intent = new Intent(this, c);
            intent.putExtra(extraKey, extraValue);
            startActivity(intent);
        }else{
            Toast.makeText(getBaseContext(), "Invalid Name", Toast.LENGTH_LONG).show();
        }
    }

    public void goBtn(View view) {
        if(webSearch.isDone()){
            changeScene(WebActivity.class, "USERNAME", username);
        }else{
            Toast.makeText(getBaseContext(), "Retrieving Data... Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    public void userBtn(View view) {
        changeScene(UserActivity.class, "USERNAME", username);
    }

    public void changeUserName(View view) {
        username = nameTxt.getText().toString();
        nameTxt.setText("");
    }

    @Override
    public void onLocationChanged(Location location) {

        /**
         * Get city name
         */

        String cityName = null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String s = cityName;
        locTxt.setText("Connecting from: " + s);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
